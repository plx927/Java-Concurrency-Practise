package com.panlingxiao.concurrency.atomic;

import sun.misc.Unsafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by panlingxiao on 2016/8/18.
 */
public class CounterClient implements Runnable {

    public static void main(String[] args) throws Exception {
        int NUM_OF_THREADS = 1000;
        int NUM_OF_INCREMENTS = 100000;
        ExecutorService service = Executors.newFixedThreadPool(NUM_OF_THREADS);

        // creating instance of specific counter
        Counter counter = new SyncCounter();

        long before = System.currentTimeMillis();

        //创建1000个线程，每个线程执行100000次。
        for (int i = 0; i < NUM_OF_THREADS; i++) {
            service.submit(new CounterClient(counter, NUM_OF_INCREMENTS));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
        long after = System.currentTimeMillis();
        System.out.println("Counter result: " + counter.getCounter());
        System.out.println("Time passed in ms:" + (after - before));

    }

    private Counter c;
    private int num;

    public CounterClient(Counter c, int num) {
        this.c = c;
        this.num = num;
    }

    @Override
    public void run() {
        for (int i = 0; i < num; i++) {
            c.increment();
        }
    }

    /**
     * Counter result: 99670872
     * Time passed in ms:130
     */
    static class StupidCounter implements Counter {
        private long counter = 0;

        @Override
        public void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }


    /**
     * Counter result: 100000000
     * Time passed in ms:4893
     */
    static class SyncCounter implements Counter {
        private long counter = 0;

        @Override
        public synchronized void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }


    /**
     * Counter result: 100000000
     * Time passed in ms:2884
     */
    static class LockCounter implements Counter {
        private long counter = 0;
        private ReentrantReadWriteLock.WriteLock lock = new ReentrantReadWriteLock().writeLock();

        @Override
        public void increment() {
            lock.lock();
            counter++;
            lock.unlock();
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    /**
     * Counter result: 100000000
     * Time passed in ms:6632
     */
    static class AtomicCounter implements Counter {
        AtomicLong counter = new AtomicLong(0);

        @Override
        public void increment() {
            counter.incrementAndGet();
        }

        @Override
        public long getCounter() {
            return counter.get();
        }
    }

    /**
     * Counter result: 100000000
     * Time passed in ms:6801
     */
    static class CASCounter implements Counter {
        private volatile long counter = 0;
        private Unsafe unsafe;
        private long offset;

        public CASCounter() throws Exception {
            unsafe = UnsafeTest.UnsafeUtil.getUnsafe();
            offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
        }

        @Override
        public void increment() {
            long before = counter;
            while (!unsafe.compareAndSwapLong(this, offset, before, before + 1)) {
                before = counter;
            }
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }
}
