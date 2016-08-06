package com.panlingxiao.concurrency.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by panlingxiao on 2016/8/6.
 * 对于基于锁的秒杀与无锁的秒杀实现
 */
public class SecondKillTest {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        final int NUM = 5000000;
        final int THREAD_NUM = 1000;
        Runnable task = new LockSecondKill(NUM);
        Thread[] threads = new Thread[THREAD_NUM];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }


        for (Thread thread : threads) {
            thread.join();
        }

        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000);

    }


    static class LockSecondKill implements Runnable {
        private int num;
        private Object lock = new Object();

        public LockSecondKill(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            synchronized (lock) {
                while (num > 0) {
                    num--;
                    System.out.println("Sale Product: " + num);
                }
            }
        }
    }

    static class UnLockSecondKill implements Runnable {
        private AtomicInteger num;

        public UnLockSecondKill(int num) {
            this.num = new AtomicInteger(num);
        }

        @Override
        public void run() {
            int value = 0;
            while ((value = num.decrementAndGet()) > 0) {
                System.out.println("Sale Product: " + value);
            }
        }
}


}
