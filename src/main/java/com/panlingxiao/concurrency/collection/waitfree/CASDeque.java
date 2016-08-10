package com.panlingxiao.concurrency.collection.waitfree;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by panlingxiao on 2016/8/8.
 */
public class CASDeque {

    public static void main(String[] args) {

        final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        /*
         * CAS预先入队列，解决IDEA调式中出现的坑
         *
         * 构建出队列的结构:
         * --------------------------------------------------------------------
         * 在Idea下执行完下面的代码后，队列的结构为:
         *
         *  Head             Tail
         *    |                 |
         *  Node(Java)-----> Node(Python) ---->Node(Docker)
         *
         *  正常执行的后队列的结构为:
         *
         *  Head                          Tail
         *   |                             |
         *  Node(null)---->Node(Java)---->Node(Python)---->Node(Docker)
         *
         * ---------------------------------------------------------------------
         */
        queue.offer("Java");
        queue.offer("Python");
        queue.offer("Docker");


        /*
         * 出队列1:
         *
         * Tail/Head
         *  |    |
         * Node(Python)---->Node(Docker)
         *
         */
        String str = queue.poll();
        System.out.println(str);


        /*
         * 出队列2:
         *
         *  Tail(此时的tail的next等于自己)
         *   |
         *  Node(Python)
         *
         *  Head
         *   |
         *  Node(Docker)
         *
         */
        str = queue.poll();
        System.out.println(str);




    }

    private static void labelTest() {
        int i = 0;
        hello:
        for (; ; ) {
            System.out.println("hello world");
            if (i == 10) {
                break;
            }
            for (; ; ) {
                i++;
                if (i % 2 == 0) {
                    continue hello;
                }
            }
        }
        System.out.println("循环结束");
    }


}
