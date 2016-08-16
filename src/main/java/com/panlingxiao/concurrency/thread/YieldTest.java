package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/16.
 */
public class YieldTest extends Thread {


    static boolean finished = false;
    static int sum = 0;

    public static void main(String[] args) {
        new YieldTest().start();

        for (int i = 1; i <= 50000; i++) {
            sum++;

            if (args.length == 0)
                Thread.yield();
        }

        finished = true;
        System.out.println("finished:"+finished);
    }

    public void run() {
        while (!finished){
           // System.out.println("sum = " + sum);
        }
    }
}

