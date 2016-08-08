package com.panlingxiao.concurrency.collection.waitfree;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by panlingxiao on 2016/8/6.
 * 基于CAS的入队列操作,DEBUG调式入队列过程
 */
public class CASEnque {

    public static void main(String[] args) {
        final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        //CAS预先入队列，解决IDEA调式中出现的坑
        queue.offer("Java");
        queue.offer("Python");
        queue.offer("Docker");

        Thread[] threads = new Thread[10];
        for(int i = 0;i < threads.length;i++){
            final int k = i;
            threads[i] = new Thread("线程:"+i){
                @Override
                public void run() {
                    for(int j = 0;j < 100;j++){
                        String str = getName() + "--hello:" + k;
                        queue.offer(str);
                    }
                }
            };
            threads[i].start();
        }


        //变态的语法
//        int a = 0;
//        int b = 1;
//        //拿原来的a和2比较，然后将2赋值给a,再拿比较结果进行判断
//        int c = ( a != (a = 2)) ? a : b;
//        System.out.println("a="+a);
//        System.out.println("c="+c);


    }
}
