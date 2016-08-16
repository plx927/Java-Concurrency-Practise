package com.panlingxiao.concurrency.collection.map;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by panlingxiao on 2016/8/16.
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
        map.put("a","hello");
        map.put("b", "world");

    }
}
