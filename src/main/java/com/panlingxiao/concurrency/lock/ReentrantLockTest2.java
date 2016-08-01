package com.panlingxiao.concurrency.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by panlingxiao on 2016/8/1.
 * 使用ReentrantLock和Condition来简单模拟线程间通信,实现生产者和消费者模型
 */
public class ReentrantLockTest2 {

    static ReentrantLock lock = new ReentrantLock();
    //Condition是和Lock先关联的
    static Condition condition = lock.newCondition();
    static Integer counter = 0;

    public static void main(String[] args) {
        Thread consumer = new Thread(new Consumer());
        Thread producer = new Thread(new Producer());
        consumer.start();
        producer.start();
    }

    static class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    while (counter == 0) {
                        condition.await();
                    }
                    counter--;
                    System.out.println("Consumer:"+counter);
                    Thread.sleep((long) (Math.random()*1000));
                    condition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }

    }

    static class Producer implements Runnable {
        @Override
        public void run() {
          while (true){
              try {
                  lock.lock();
                  while (counter == 1){
                      condition.await();
                  }
                  counter ++;
                  System.out.println("Produce:"+counter);
                  Thread.sleep((long) (Math.random() * 1000));
                  condition.signalAll();
              } catch (Exception e) {
                  e.printStackTrace();
              }finally {
                  lock.unlock();
              }
          }
        }
    }


}
