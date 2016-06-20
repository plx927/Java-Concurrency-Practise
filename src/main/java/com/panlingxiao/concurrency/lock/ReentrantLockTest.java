package com.panlingxiao.concurrency.lock;

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
        lock.lock();
    }
}
