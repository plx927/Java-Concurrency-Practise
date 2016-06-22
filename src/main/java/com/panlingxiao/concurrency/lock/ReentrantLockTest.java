package com.panlingxiao.concurrency.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by panlingxiao on 2016/6/20.
 * ReentrantLock基本使用以及源码分析:
 *
 * 在ReentrantLock中，其底层使用了AQS的状态值来表示所被持的次数
 *
 */
public class ReentrantLockTest {

    //底层创建一个同步对象，实现非公平锁
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(){
            @Override
            public void run() {
                try{
                    //ReentrantLock具有可重入性
                    lock.lock();
                    lock.lock();
                    System.out.println("Lock test!");
                    latch.countDown();
                }finally {
                    lock.unlock();
                    lock.unlock();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                try {
                    latch.await();
                    lock.lock();
                    /*
                     * 当锁被其他线程所占用时，ReentrantLock会是当前线程进入到等待状态
                     * 而使用synchronized(内置锁)，在会让线程进入到阻塞状态
                     */
                    System.out.println("Lock test2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }.start();
    }
}
