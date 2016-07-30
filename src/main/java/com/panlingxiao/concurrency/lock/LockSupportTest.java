package com.panlingxiao.concurrency.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by panlingxiao on 2016/6/24.
 */
public class LockSupportTest {

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    LockSupport.getBlocker(this);
                    System.out.println("线程执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        LockSupport.unpark(thread);
        countDownLatch.countDown();
    }
}
