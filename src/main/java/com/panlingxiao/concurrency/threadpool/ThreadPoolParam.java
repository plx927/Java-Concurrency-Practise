package com.panlingxiao.concurrency.threadpool;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolParam {


    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int RUNNING = (-1 << COUNT_BITS);
    private static final int SHUTDOWN = (0 << COUNT_BITS);
    private static final int TIDYING = (2 << COUNT_BITS);

    // 2^29-1=000,1...1
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    public static void main(String[] args) {
        // 111,0....0
        System.out.println("Running:" + RUNNING);

        // 000,0....0
        System.out.println("SHUTDOWN:" + SHUTDOWN);

        // 001,0....0
        System.out.println("STOP:" + (1 << COUNT_BITS));

        // 010,0....0
        System.out.println("TIDYING:" + TIDYING);

        // 011,0....0
        System.out.println("TERMINATED:" + (3 << COUNT_BITS));

        // 111,0....0
        AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
        System.out.println("ctl:" + ctl);

        System.out.println(runStateAtLeast(ctl.get(), TIDYING));

    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }


    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }
}
