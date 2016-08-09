package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/9.
 * 多线程通信时使用notify引发所有线程都处于wait的情况
 */
public class WaitNotifyTest {

    static int counter = 0;
    static Object lock = new Object();

    public static void main(String[] args) {
        ProThread p1 = new ProThread("p1");
        ProThread p2 = new ProThread("p2");

        ConThread c1 = new ConThread("c1");
        ConThread c2 = new ConThread("c2");

        p1.start();
        p2.start();

        c1.start();
        c2.start();
    }

    //生产线程
    static class ProThread extends Thread {

        public ProThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    try {
                        while (counter == 1) {
                            lock.wait();
                        }
                        counter++;
                        System.out.println(getName() + ":counter:" + counter);
                        lock.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    //消费线程
    static class ConThread extends Thread {
        ConThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    try {
                        while (counter == 0) {
                            lock.wait();
                        }
                        counter--;
                        System.out.println(getName() + ":counter:" + counter);
                        lock.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
