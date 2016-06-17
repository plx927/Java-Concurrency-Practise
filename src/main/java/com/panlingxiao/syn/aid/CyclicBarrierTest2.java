package com.panlingxiao.syn.aid;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by panlingxiao on 2016/6/17.
 * 测试当线程中断而提前离开“barrier point”时所造成的结果
 */
public class CyclicBarrierTest2 {

    static  CyclicBarrier cyclicBarrier;
    static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws InterruptedException{
        final int N = 3;
        final String[] strings = {"A","B","C"};
         cyclicBarrier = new CyclicBarrier(N, new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName()+"说:解散!");
            }
        });
        MyThread myThread1 = new MyThread("A");
        myThread1.start();
        countDownLatch.await();
        //中断第一个线程
        myThread1.interrupt();

        for(int i = 1;i < N;i++){
            new MyThread(strings[i]).start();
        }
    }

    static class MyThread extends Thread{
        public MyThread(String name){
            super(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"说:到!");
            countDownLatch.countDown();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName()+"被中断了");
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "栅栏被破坏了");
            }
        }
    }




}
