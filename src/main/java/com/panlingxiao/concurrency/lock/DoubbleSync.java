package com.panlingxiao.concurrency.lock;

/**
 * Created by panlingxiao on 2016/7/31.
 * Synchronized关键字的可重入性
 * 参考文章:http://blog.csdn.net/ns_code/article/details/17014135
 */
public class DoubbleSync {

    public synchronized  void method1(){
        System.out.println("method1");
        /*
         * 当某个线程已经获取到锁后,如果再次调用同步方法，此时可以再次获取到锁
         * 如果内置锁是不可重入的，那么这样就会产生死锁。
         * 因为在执行method1的时候,线程已经获取到对象锁,但是假设不可重入,
         * 那么执行method2的时候必须等待先执行完method1释放锁才能继续执行，
         * 这显示会造成死锁问题的发生。
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
