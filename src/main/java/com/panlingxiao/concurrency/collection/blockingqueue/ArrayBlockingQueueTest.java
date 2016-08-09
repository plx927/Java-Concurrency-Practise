package com.panlingxiao.concurrency.collection.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by panlingxiao on 2016/8/9.
 */
public class ArrayBlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

        queue.offer("hello");
        queue.offer("world");
        queue.offer("welcome");
        queue.offer("Java");
        queue.add("abc");


        System.out.println("size:" + queue.size());

        String element = queue.peek();
        System.out.println("element:"+element);

        System.out.println("size:" + queue.size());



        boolean result = queue.offer("def");
        System.out.println("result:"+result);
        //这个方法会阻塞，直到容器有空间可以存储
        //queue.put("hello world!");

        //会抛出异常Queue Full
        //queue.add("ni hao");
    }
}
