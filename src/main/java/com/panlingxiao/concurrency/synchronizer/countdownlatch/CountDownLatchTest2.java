package com.panlingxiao.concurrency.synchronizer.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by panlingxiao on 2016/6/20.
 * 验证JDK中的注释说明
 */
public class CountDownLatchTest2 {

    public static void main(String[] args) throws InterruptedException {
        int N = 10;
        final CountDownLatch startSignal = new CountDownLatch(1);
        final CountDownLatch doneSignal = new CountDownLatch(N);

        for (int i = 0; i < N; i++) {
            new Thread(new Worker(startSignal, doneSignal),"线程"+i).start();
        }
        //当主线程执行完毕后，将countDownLatch的count值设置为0，让其他线程继续执行
        doSomethingElse();
        startSignal.countDown();
        //主线程进入到等待状态,直到其他线程都执行countDown，将count值设置0时，主线程才继续执行
        doneSignal.await();
        doSomethingElse2();
    }


    private static void doSomethingElse() {
        System.out.println("主线程开始处理");
    }

    private static void doSomethingElse2() {
        System.out.println("主线程结束处理");
    }


    static class Worker implements Runnable {
        final CountDownLatch startSignal;
        final CountDownLatch doneSignal;

        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await();
                doWork();
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void doWork() {
            System.out.println("子线程"+Thread.currentThread().getName()+"开始处理");
        }
    }
}
