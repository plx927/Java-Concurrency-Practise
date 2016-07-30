package com.panlingxiao.concurrency.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by panlingxiao on 2016/7/28.
 */
public class CopyOnWriteArrayListTest {


    public static void main(String[] args) {

        //concurrentModifyTest();

        copyOnWrite();

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
    static void concurrentModifyTest() {
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
