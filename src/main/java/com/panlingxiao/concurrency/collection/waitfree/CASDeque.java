package com.panlingxiao.concurrency.collection.waitfree;

/**
 * Created by panlingxiao on 2016/8/8.
 */
public class CASDeque {

    public static void main(String[] args) {
       // labelTest();
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
