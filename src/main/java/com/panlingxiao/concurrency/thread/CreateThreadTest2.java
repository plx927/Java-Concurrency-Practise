package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/14.
 * 通过实现Runnable接口的方式来创建线程
 */
public class CreateThreadTest2 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();


    }

    /**
     * 通过实现Runnable接口的方法来创建线程
     */
    static class MyTask implements Runnable{
        @Override
        public void run() {
            for(int i = 0 ;i < 10;i++)
                System.out.println(Thread.currentThread().getName()+" say : HelloWorld!"+i);
        }
    }

}
