package com.panlingxiao.concurrency.collection.waitfree;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by panlingxiao on 2016/8/10.
 */
public class SentinelNode {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");


        /*
         * 执行完size或者peek方法，如果head节点的item为null,则会让head节点的指向其next节点
         */
        System.out.println(queue.peek());





    }
}
