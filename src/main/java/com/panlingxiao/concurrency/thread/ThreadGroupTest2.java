package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/15.
 */
public class ThreadGroupTest2 {

    public static void main(String[] args) throws InterruptedException {

        final ThreadGroup tg = new ThreadGroup("myGroup");
        Thread t1 = new Thread(tg, new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ;i < 10;i++){
                    try {
                        Thread.sleep((long)(Math.random()*1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t1");

        Thread t2 = new Thread(tg, new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this){
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2");

        t1.start();
        t2.start();


        Thread monitor = new Thread(new Runnable() {
            @Override
            public void run() {
                //检查线程组中线程
                while(true){
                    int activeCount = tg.activeCount();
                    System.out.println("activeCount:"+activeCount);
                    tg.list();
                    try {
                        Thread.sleep((long)(Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        monitor.setDaemon(true);
        monitor.start();









    }
}
