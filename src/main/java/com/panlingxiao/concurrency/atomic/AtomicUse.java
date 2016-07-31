package com.panlingxiao.concurrency.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by panlingxiao on 2016/7/31.
 */
public class AtomicUse {

    static AtomicInteger counter = new AtomicInteger(0);

    public void multiAdd(){
        counter.addAndGet(1);
        counter.addAndGet(2);
        counter.addAndGet(3);
        counter.addAndGet(4);
    }


    public static void main(String[] args) throws InterruptedException {
        final AtomicUse atomicUse = new AtomicUse();
        final int THREAD_NUM = 1000;
        Thread[] threads = new Thread[THREAD_NUM];
        for(int i = 0;i < threads.length;i++){
            threads[i] = new Thread(){
                @Override
                public void run() {
                    atomicUse.multiAdd();
                }
            };
            threads[i].start();
        }

        for(Thread thread : threads){
            thread.join();
        }

        System.out.println(counter.get());

    }
}
