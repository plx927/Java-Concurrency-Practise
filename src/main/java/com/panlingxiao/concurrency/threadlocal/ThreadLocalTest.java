package com.panlingxiao.concurrency.threadlocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by panlingxiao on 2016/7/22.
 * ThreadLocal使用以及原理分析
 */
public class ThreadLocalTest {



    public static void main(String[] args) throws InterruptedException {

        final ThreadID threadID = new SafeThreadID();
        final int THREAD_NUM = 5;

        /*
         * 多个线程并发操作同一个变量
         */
        Thread[] threads = new Thread[THREAD_NUM];
        for (int i = 0 ;i < threads.length;i++){
            final int j = i;
            threads[i] = new Thread("Thread:"+j){
                @Override
                public void run() {
                    threadID.set(j);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(getName()+"'s threadId is: "+ threadID.get());
                }
            };
            threads[i].start();
        }

    }


    static interface  ThreadID{
        public Integer get();

        public void set(Integer id);
    }

    static class SafeThreadID implements ThreadID{
        private static  AtomicInteger counter = new AtomicInteger();
        private  static ThreadLocal<Integer> threadId = new ThreadLocal<Integer>(){
           @Override
           protected Integer initialValue() {
               return counter.incrementAndGet();
           }
       };

       // private static MyThreadLocal<Integer> threadId = new MyThreadLocal<>();

        public Integer get(){
            return threadId.get();
        }

        public void set(Integer id){
            threadId.set(id);
        }
    }



    static class UnsafeThreadId implements  ThreadID{
        Integer id  ;
        public synchronized void set(Integer id){
            this.id = id;
        }
        public  Integer get(){
            return id;
        }
    }


    static class MyThreadLocal<T>{
        private Map<Thread,T> map = new ConcurrentHashMap<Thread,T>();
        public void set(T t){
            map.put(Thread.currentThread(),t);
        }

        public T get(){
            return map.get(Thread.currentThread());
        }
    }
}

