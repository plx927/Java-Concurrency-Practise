package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/12.
 */
public class ThreadGroupInterruptTest {
    public static void main(String[] args) {
        MyThread mt = new MyThread();
        mt.setName("A");
        mt.start();
        mt = new MyThread();
        mt.setName("B");
        mt.start();
        try {
            Thread.sleep(2000); // Wait 2 seconds
        } catch (InterruptedException e) {

        }
        // Interrupt all methods in the same thread group as the main
        // thread
        Thread.currentThread().getThreadGroup().interrupt();
    }

    static class MyThread extends Thread {
        public void run() {
            synchronized ("A") {
                System.out.println(getName() + " about to wait.");
                try {
                    "A".wait();
                } catch (InterruptedException e) {
                    System.out.println(getName() + " interrupted.");
                }
                System.out.println(getName() + " terminating.");
            }
        }
    }
}
