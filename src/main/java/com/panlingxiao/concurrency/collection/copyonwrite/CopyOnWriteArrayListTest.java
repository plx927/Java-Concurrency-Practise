package com.panlingxiao.concurrency.collection.copyonwrite;

import com.panlingxiao.concurrency.collection.unsafe.TestUnsafeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by panlingxiao on 2016/7/28.
 */
public class CopyOnWriteArrayListTest {


    public static void main(String[] args) throws InterruptedException {

        //concurrentModifyTest();

        //copyOnWrite();
        concurrentAdd();
    }


    /**
     * 对比{@link TestUnsafeList#main(String[])}方法进行对比
     * @throws InterruptedException
     */
    public static  void concurrentAdd() throws InterruptedException {
        final List<String> list = new CopyOnWriteArrayList<String>();
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

    /**
     * 基于CopyOnWrite实现在读取数据，如果对数据进行修改,
     * 此时对原来的数据产生一份快照，用于读取到的数据是快照数据
     */
    private static void copyOnWrite() {
        List<String> list = new CopyOnWriteArrayList<String>(Arrays.asList("hello", "world", "Java"));
        for(Iterator<String> iterator = list.iterator();iterator.hasNext(); ){
            String next = iterator.next();
            System.out.println(next);
            list.add("Spring");
        }

        System.out.println("------------------------");
        System.out.println(list);
    }


    /**
     * 在迭代集合时，对集合的结构进行修改引发ConcurrentModificationException
     */
    static void concurrentModifyArrayList() {
        List<String> list = new ArrayList<String>(Arrays.asList("hello", "world", "Java"));
        for(Iterator<String> iterator = list.iterator();iterator.hasNext(); ){
            String next = iterator.next();
            System.out.println(next);
            list.add("Spring");
        }
        System.out.println("------------------------");
        System.out.println(list);

    }
}
