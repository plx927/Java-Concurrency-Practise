package com.panlingxiao.concurrency.collection;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by panlingxiao on 2016/8/4.
 * Debug调式ConcurrentLinkedQueue对于CAS入队操作的处理
 */
public class ConcurrentLinkedQueueExample2 {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
        queue.offer("hello");


//        final int THREAD_NUM = 2;
//        Thread[] threads = new Thread[THREAD_NUM];
//        for(int i = 0;i < THREAD_NUM;i++){
//            final int j = i;
//            threads[i] = new Thread(){
//                @Override
//                public void run() {
//                    queue.offer("hello:"+j);
//                }
//            };
//            threads[i].start();
//        }



    }
}
