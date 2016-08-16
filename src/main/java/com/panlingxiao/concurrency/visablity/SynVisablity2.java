package com.panlingxiao.concurrency.visablity;

/**
 * Created by panlingxiao on 2016/8/16.
 * 解释为什么synchronized加到了run方法上没有保证内存可见性
 * 原因:
 * 保证可见性的原理是每次加锁的时候清空缓存，每次解锁的时候更新内存
 * 这里只是一开始清空了下缓存,之后没更新过内存
 */
public class SynVisablity2 {

    public static boolean flag = true;


    public static class MyThread extends Thread {
        @Override
        public synchronized void run() {
            while (flag) {

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new MyThread().start();
        //确保线程启动
        Thread.sleep(100);
        SynVisablity.flag = false;
    }
}
