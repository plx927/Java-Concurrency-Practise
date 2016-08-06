package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/6.
 * 线程状态测试，测试线程重复执行start方法所抛出的异常。
 */
public class ThreadStatusTest {
    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        };
        thread.start();

//        new Thread(){
//            @Override
//            public void run() {
//                thread.start();
//            }
//        }.start();


        //当到上面的线程执行完毕后再继续执行
        thread.join();

        System.out.println("Thread isAlive: " + thread.isAlive());

        //再次启动线程，抛出异常
        thread.start();

    }
}
