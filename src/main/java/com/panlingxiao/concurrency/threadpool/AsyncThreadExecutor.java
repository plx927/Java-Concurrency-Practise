package com.panlingxiao.concurrency.threadpool;


import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.stream.IntStream;


public class AsyncThreadExecutor implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(AsyncThreadExecutor.class);


    private static final int DEFAULT_QUEUE_SIZE = 1000;

    private static final int DEFAULT_POOL_SIZE = 10;

    private int queueSize = DEFAULT_QUEUE_SIZE;

    private int poolSize = DEFAULT_POOL_SIZE;


    /**
     * 用于周期性监控线程池的运行状态
     */
    private final ScheduledExecutorService scheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor(
                    new BasicThreadFactory.Builder().namingPattern("async thread executor monitor").build());


    private final ExecutorService executorService = Executors.newCachedThreadPool();
    /**
     * 自定义异步线程池
     * （1）任务队列使用有界队列
     * （2）自定义拒绝策略
     */
    private  ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(poolSize, poolSize, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueSize),
                    new BasicThreadFactory.Builder().namingPattern("async-thread-%d").build(),
                    (r, executor) -> log.error("the async executor pool is full!!"));




    public void init() {
        threadPoolExecutor = (ThreadPoolExecutor) executorService;
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            /**
             * 线程池需要执行的任务数
             */
            long taskCount = threadPoolExecutor.getTaskCount();
            /**
             * 线程池在运行过程中已完成的任务数
             */
            long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
            /**
             * 曾经创建过的最大线程数
             */
            long largestPoolSize = threadPoolExecutor.getLargestPoolSize();
            /**
             * 线程池里的线程数量
             */
            long poolSize = threadPoolExecutor.getPoolSize();
            /**
             * 线程池里活跃的线程数量
             */
            long activeCount = threadPoolExecutor.getActiveCount();


            int corePoolSize = threadPoolExecutor.getCorePoolSize();

            int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();


            log.info("async-executor monitor. taskCount:{}, completedTaskCount:{},largestPoolSize:{}, poolSize:{}," +
                            " activeCount:{},corePoolSize:{},maximumPoolSize:{}",
                    taskCount, completedTaskCount, largestPoolSize, poolSize, activeCount,
                    corePoolSize,maximumPoolSize);
        }, 0, 1, TimeUnit.SECONDS);
    }


    public void execute(Runnable task) {
        executorService.execute(task);
    }

    @Override
    public void close() {
        executorService.shutdown();
    }


    public static void main(String[] args) {
        AsyncThreadExecutor asyncThreadExecutor = new AsyncThreadExecutor();
        asyncThreadExecutor.init();
        IntStream.range(0, 200).forEach(value -> {
            asyncThreadExecutor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName()+" execute:" + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

    }


}
