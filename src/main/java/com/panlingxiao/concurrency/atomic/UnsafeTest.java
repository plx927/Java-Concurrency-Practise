package com.panlingxiao.concurrency.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by panlingxiao on 2016/8/16.
 * 在Java中使用Unsafe
 */
public class UnsafeTest {

    public static void main(String[] args) {

        /*
         * 直接获取Unsafe会引发java.lang.SecurityException: Unsafe
         */

        //  Unsafe.getUnsafe();

        Unsafe u = UnsafeUtil.getUnsafe();
        int[] a = new int[10];
        //获取数组中第一个元素相对于数组在内存中的偏移量
        int baseOffset = u.arrayBaseOffset(a.getClass());
        //获取数组中每个元素的大小
        int indexScale = u.arrayIndexScale(a.getClass());
        System.out.println("baseOffset:" + baseOffset);
        System.out.println("indexScale:" + indexScale);


        baseOffset = u.arrayBaseOffset(String[].class);
        indexScale = u.arrayIndexScale(String[].class);
        System.out.println("baseOffset:" + baseOffset);
        System.out.println("indexScale:" + indexScale);



        baseOffset = u.arrayBaseOffset(double[].class);
        indexScale = u.arrayIndexScale(double[].class);
        System.out.println("baseOffset:" + baseOffset);
        System.out.println("indexScale:" + indexScale);

    }


    public static class UnsafeUtil {
        public static Unsafe unsafe = getUnsafe();

        public static Unsafe getUnsafe() {
            try {
                Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafeInstance.setAccessible(true);
                Unsafe u = (Unsafe) theUnsafeInstance.get(Unsafe.class);
                return u;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
