package com.panlingxiao.concurrency.synchronizer.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by panlingxiao on 2016/6/21.
 * 使用CountDownLatch完成一个MapReduce的功能
 */
public class CountDownLatchTest3 {
    public static void main(String[] args) throws Exception{
        final int CORE = Runtime.getRuntime().availableProcessors() * 2;
        ExecutorService executorService = Executors.newFixedThreadPool(CORE);
        final int MAPPER_NUMBER = 20;
        CountDownLatch countDownLatch = new CountDownLatch(MAPPER_NUMBER);
        for(int i = 0;i < MAPPER_NUMBER;i++){
            executorService.execute(new Mapper(String.valueOf("Mapper"+i),countDownLatch));
        }
        countDownLatch.await();
        new Thread(new Reducer()).start();
        //关闭线程池
        executorService.shutdown();
    }

    static class Reducer implements Runnable {

        @Override
        public void run() {
            System.out.println("执行Reduce");
        }
    }


    static class Mapper implements Runnable {
        String name;
        CountDownLatch countDownLatch;
        public Mapper(String name,CountDownLatch countDownLatch){
            this.name = name;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println(this.name+"执行Mapper");
            countDownLatch.countDown();
        }
    }
}
