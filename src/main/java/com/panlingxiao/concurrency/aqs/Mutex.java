package com.panlingxiao.concurrency.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by panlingxiao on 2016/6/24.
 * 使用AQS实现一个不可重入的互斥锁
 * 当AQS的状态值为0时，表示未锁定状态
 */
public class Mutex implements Lock {

    /**
     * 将AQS的子类定义成一个私有的内部类
     */
    private class Sync extends AbstractQueuedSynchronizer {
        //判断当前同步器是否被其他线程所持有
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }


        /**
         * 尝试获取锁,当state值为0时，获取成功
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            //尝试使用CAS去改变AQS的状态值
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 尝试锁，通过将状态值设置为0
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0)
                throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public static void main(String[] args) {

    }

}
