package com.panlingxiao.concurrency.lock;

/**
 * Created by panlingxiao on 2016/8/1.
 * 使用内置锁来模拟生成者和消费者模型，类比ReentrantLockTest2
 */
public class WaitNotifyTest {

    public static void main(String[] args) {
        Thread consumer = new Thread(new Consumer());
        Thread producer = new Thread(new Producer());
        consumer.start();
        producer.start();
    }


    static int counter = 0;
    static Object monitor = new Object();

    static class Producer implements Runnable{
        @Override
        public  void run() {
            while(true){
                synchronized (monitor){
                    try {
                        while (counter == 1){
                            //IllegalMonitorStateException
                           // this.wait();

                            /*
                             * 执行wait方法会让当前线程释放锁(Monitor)的拥有权
                             * 当它被唤醒后需要重新获取锁的拥有权才能继续执行
                             */
                            monitor.wait();
                        }
                        counter ++;
                        System.out.println("Produce:" + counter);
                        Thread.sleep((long) (Math.random() * 1000));
                        monitor.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    static class Consumer implements Runnable{

        @Override
        public void run() {
            while(true){
                synchronized (monitor){
                    try {
                        while (counter == 0){
                            monitor.wait();
                        }
                        counter--;
                        System.out.println("Consumer:"+counter);
                        Thread.sleep((long) (Math.random() * 1000));
                        monitor.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
