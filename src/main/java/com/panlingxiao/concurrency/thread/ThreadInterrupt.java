package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/1.
 * 线程中断问题
 */
public class ThreadInterrupt {

    public static void main(String[] args) {
        /**
         * 线程中断
         */
        Thread thread = new Thread(){
            @Override
            public void run() {
                System.out.println(String.format("Thread is interrupted: %s",this.isInterrupted()));
                //中断线程，设置线程的中断标志位
                this.interrupt();
                System.out.println(String.format("Thread is interrupted: %s", this.isInterrupted()));
                try {
                    //此时因为中断标记位已经被设置，因此会抛出中断异常
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    //如果线程已经设置了中断标记位，那么线程的标记会被被清除，然后抛出异常
                    System.out.println(String.format("Throw Exception,thread is interrupted: %s", this.isInterrupted()));
                }
            }
        };
        thread.start();

        //中断主线程
        Thread.currentThread().interrupt();
        //判断当前线程是否已经中断,如果已经中断，则清除标记位
        System.out.println(String.format("Main Thread is interrupted %s",Thread.interrupted()));
        System.out.println(String.format("Main Thread is interrupted %s",Thread.interrupted()));

    }
}
