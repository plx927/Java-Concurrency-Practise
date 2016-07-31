package com.panlingxiao.concurrency.lock;

/**
 * Created by panlingxiao on 2016/7/31.
 * Synchronized关键字的可重入性
 */
public class DoubbleSync {

    public synchronized  void method1(){
        System.out.println("method1");
        /*
         * 当某个线程已经获取到锁后,如果再次调用同步方法，此时可以再次获取到锁
         */
        method2();
    }

    public synchronized  void method2() {
        System.out.println("method2");
    }

    public static void main(String[] args) {
        final DoubbleSync doubbleSync = new DoubbleSync();
        new Thread(){
            @Override
            public void run() {
                doubbleSync.method1();
            }
        }.start();
    }
}
