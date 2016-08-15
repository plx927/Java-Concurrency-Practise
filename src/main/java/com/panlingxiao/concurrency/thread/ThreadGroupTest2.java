package com.panlingxiao.concurrency.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by panlingxiao on 2016/8/15.
 * 启动两个线程，添加到同一个线程组中。
 * 通过另外一个线程检测线程组中的线程状态
 * 当线程组中的线程全部死亡后，可以销毁线程组。
 */
public class ThreadGroupTest2 {

    public static void main(String[] args) throws InterruptedException {

        final ThreadGroup tg = new ThreadGroup("myGroup");
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        final Thread t1 = new Thread(tg, new Runnable() {
            @Override
            public void run() {
                countDownLatch.countDown();
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1");

        final Thread t2 = new Thread(tg, new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.countDown();
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupt...");
                }
            }
        }, "t2");

        //未启动线程前输出
        System.out.println("before start threads,activeCount:"+tg.activeCount());

        t1.start();
        t2.start();

        //启动线程后输出
        System.out.println("after start threads,activeCount:" + tg.activeCount());


        Thread monitor = new Thread(new Runnable() {
            @Override
            public void run() {
                int activeCount = tg.activeCount();
                //检查线程组中线程
                while (activeCount > 0) {
                    tg.list();
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    activeCount = tg.activeCount();
                    System.out.println("activeCount:" + activeCount);
                    if (activeCount == 1) {
                        tg.interrupt();
                        //t2.interrupt();
                    }
                }
                if (!tg.isDestroyed()) {
                    tg.destroy();
                    System.out.println("ThreadGroup destroy ....");
                }else{
                    System.out.println("ThreadGroup has been destroyed ...");
                }
            }
        });
        //monitor.setDaemon(true);W
        monitor.start();


    }
}
