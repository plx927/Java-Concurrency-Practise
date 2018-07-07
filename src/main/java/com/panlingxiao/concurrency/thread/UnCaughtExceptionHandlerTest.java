package com.panlingxiao.concurrency.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by panlingxiao on 2018/7/7.
 */
public class UnCaughtExceptionHandlerTest {

    static class MyHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            // 当频繁抛出异常时，JVM会对栈做优化，因而无法获取具体的异常堆栈信息
            String message = e.getMessage();
            if (message == null || message.length() == 0) {
                System.out.println(message);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler(new MyHandler());
        for (int i = 0; i < 100000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(1 / 0);
                }
            }, "MyThread").start();
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }
}
