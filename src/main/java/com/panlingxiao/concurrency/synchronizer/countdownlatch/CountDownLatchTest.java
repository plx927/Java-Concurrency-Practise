package com.panlingxiao.concurrency.synchronizer.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by panlingxiao on 2016/6/17.
 * CountDownLatch
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread("A线程") {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    System.out.println("A线程执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        TimeUnit.SECONDS.sleep(1);
        new Thread("B线程") {
            @Override
            public void run() {
                System.out.println("B线程执行");
                countDownLatch.countDown();
            }
        }.start();


    }
}
