package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/5.
 * <p/>
 * 一.理解并发(Concurrent)和并行(Parallel)的区别
 *
 * Java创建线程的两种方式
 * 1、继承Thread类
 * 2、实现Runnable接口
 * <p/>
 * 初学者常犯的错误:
 * 1、使用run方法来启动线程
 * 2、
 *
 *
 * 提出的问题:
 * 1.为什么要提供两种方式来创建线程？
 *
 * --从线程创建和启动的源码分析来理解。
 *
 * 2.哪种方式更好?
 *
 */
public class CreateThreadTest {

    public static void main(String[] args) {
        //这是实现类Thread的匿名内部类
        Thread thread = new Thread() {

        };
        System.out.println(thread.getClass() == Thread.class);
        System.out.println(thread.getClass());



        Thread thread1 = new MyThread();
        Thread thread2 = new MyThread();

//        thread1.start();
//        thread2.start();

        /**
         * 错误地使用run方法来启动线程，这样的执行结果为串行执行。
         */
//        thread1.run();
//        thread2.run();

        Runnable runnable = new MyTask();
        Thread thread3 = new Thread(thread1);
        Thread thread4 = new Thread(thread2);
        thread3.start();
        //thread4.start();

        /*
         * IO密集型和计算密集型
         * IO密集型:CPU*2
         * 计算密集型:CPU+1
         */
        //获取CPU的个数
//        int core = Runtime.getRuntime().availableProcessors();
//        Executors.newFixedThreadPool(core);


        //Command Design Pattern /Strategy Design Pattern
    }

    /**
     * 通过继承Thread类来创建线程
     */
    static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(String.format("Hello,I'm thread %s!", getName()));
            }
        }
    }


    /**
     * 通过实现Runnable接口的方法来创建线程
     */
    static class MyTask implements Runnable{
        @Override
        public void run() {
            for(int i = 0 ;i < 10;i++)
            System.out.println(Thread.currentThread().getName()+" say : HelloWorld!");
        }
    }


}
