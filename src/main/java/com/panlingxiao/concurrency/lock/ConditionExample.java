package com.panlingxiao.concurrency.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by panlingxiao on 2016/8/9.
 * 题目：有A,B,C三个线程, A线程输出A, B线程输出B, C线程输出C，要求, 同时启动三个线程, 按顺序输出ABC, 循环10次。
 */
public class ConditionExample {

    static ReentrantLock lock = new ReentrantLock();
    static Condition conditionA = lock.newCondition();
    static Condition conditionB = lock.newCondition();
    static Condition conditionC = lock.newCondition();
    static int counter = 0;

    public static void main(String[] args) {
        ThreadA a = new ThreadA();
        ThreadB b = new ThreadB();
        ThreadC c = new ThreadC();
        a.start();
        b.start();
        c.start();
    }

    static class ThreadA extends Thread {
        @Override
        public void run() {
            try {
                while (counter < 30) {
                    lock.lock();
                    while ((counter % 3) != 0) {
                        conditionA.await();
                    }
                    if (counter < 30) {
                        counter++;
                        System.out.println("A" + counter);
                    }
                    conditionB.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    static class ThreadB extends Thread {
        @Override
        public void run() {
            try {
                while (counter < 30) {
                    lock.lock();
                    while ((counter % 3) != 1) {
                        conditionB.await();
                    }
                    if (counter < 30) {
                        counter++;
                        System.out.println("B" + counter);
                    }
                    conditionC.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    static class ThreadC extends Thread {
        @Override
        public void run() {
            try {
                while (counter < 30) {
                    lock.lock();
                    while ((counter % 3) != 2) {
                        conditionC.await();
                    }
                    if (counter < 30) {
                        counter++;
                        System.out.println("C" + counter);
                    }
                    conditionA.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

}
