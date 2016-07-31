package com.panlingxiao.concurrency.visablity;

/**
 * Created by panlingxiao on 2016/7/31.
 * 测试Synchronized关键字对于临界区变量的可见性
 */
public class SynVisablity {

    public static boolean flag = true;

    /**
     * 加入了synchronized关键字之后,可以确保线程能够实时地获取到临界区中的变量的最新值
     */
    public static boolean getFlag(){
        return flag;
    }

    public static class MyThread extends Thread{
        @Override
        public void run() {
            while(getFlag()){

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
