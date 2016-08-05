package com.panlingxiao.concurrency.collection.unsafe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panlingxiao on 2016/6/20.
 * 测试ArrayList的非线程安全性
 * 结果可能会出现3种情况
 * 1.正常执行
 * 2.由于List结构被破坏而造成数组越界
 * 3.得到的元素个数小于总添加的个数
 */
public class TestUnsafeList {


    public static void main(String[] args) throws InterruptedException {
        final List<String> list = new ArrayList<String>();
        final int THREAD_NUM = 3;
        final int ADD_NUM = 10000;
        final Thread[] threads = new Thread[THREAD_NUM];
        //启动3个线程,并发添加数据到集合中，理论上应该添加30000个元素
        for (int i = 0; i < THREAD_NUM; i++) {
            threads[i] = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < ADD_NUM; i++) {
                        list.add(String.valueOf(i));
                    }
                }
            };
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(list.size());


    }
}
