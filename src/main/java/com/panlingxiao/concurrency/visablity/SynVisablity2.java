package com.panlingxiao.concurrency.visablity;

/**
 * Created by panlingxiao on 2016/8/16.
 * 解释为什么synchronized加到了run方法上没有保证内存可见性
 */
public class SynVisablity2 {

    public static boolean flag = true;


    public static class MyThread extends Thread{
        @Override
        public synchronized  void run() {
            while(true){

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new MyThread().start();
        //确保线程启动
        Thread.sleep(100);
        SynVisablity.flag = false;
    }
}
