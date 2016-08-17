package com.panlingxiao.concurrency.atomic;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Created by panlingxiao on 2016/8/17.
 * 让数组元素可以执行原子化地更新操作。
 */
public class AtomicReferenceArrayTest {

    public static void main(String[] args) {

        AtomicReferenceArray<String> array = new AtomicReferenceArray<String>(10);

    }
}
