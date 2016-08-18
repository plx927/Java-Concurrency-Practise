package com.panlingxiao.concurrency.atomic;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Created by panlingxiao on 2016/8/17.
 * 让数组元素可以执行原子化地更新操作。
 *
 * 深入理解AtomicReferenceArray底层对于数字的处理
 */
public class AtomicReferenceArrayTest {

    public static void main(String[] args) {

        AtomicReferenceArray<String> array = new AtomicReferenceArray<String>(10);
        //scale:4,对应二进制000...100,前面为29个0.
        int scale = UnsafeTest.UnsafeUtil.getUnsafe().arrayIndexScale(Object[].class);
        int shift = 31 - Integer.numberOfLeadingZeros(scale);
        System.out.println("scale:"+scale+",shift:"+shift);

        System.out.println(1 << shift);
    }
}
