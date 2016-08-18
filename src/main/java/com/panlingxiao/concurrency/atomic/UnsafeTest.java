package com.panlingxiao.concurrency.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by panlingxiao on 2016/8/16.
 * 在Java中使用Unsafe,参考：http://ifeve.com/sun-misc-unsafe/
 */
public class UnsafeTest {

    public static void main(String[] args) throws InstantiationException {

        /*
         * 直接获取Unsafe会引发java.lang.SecurityException: Unsafe
         * 因为它需要我们的的类是根加载器进行加载，可以使用
         * java -Xbootclasspath:/usr/jdk1.7.0/jre/lib/rt.jar:. com.panlingxiao.concurrency.atomic.UnsafeTest
         * 来强制让根加载器加载我们自己所编写类
         */
        //  Unsafe.getUnsafe();

        Unsafe u = UnsafeUtil.getUnsafe();
        int[] a = new int[10];
        //获取数组中第一个元素相对于数组在内存中的偏移量
        int baseOffset = u.arrayBaseOffset(a.getClass());
        //获取数组中每个元素的大小
        int indexScale = u.arrayIndexScale(a.getClass());
        System.out.println("int[] baseOffset:" + baseOffset);
        System.out.println("int[] indexScale:" + indexScale);//4


        baseOffset = u.arrayBaseOffset(String[].class);
        indexScale = u.arrayIndexScale(String[].class);
        System.out.println("String[] baseOffset:" + baseOffset);

        //这里是4是因为引用数据类型底层的指针占用了4个字节
        System.out.println("String[] indexScale:" + indexScale); //4


        baseOffset = u.arrayBaseOffset(double[].class);
        indexScale = u.arrayIndexScale(double[].class);
        System.out.println("double[] baseOffset:" + baseOffset);
        System.out.println("double[] indexScale:" + indexScale); //8

        System.out.println("------------------operation memory-----------------------");
        operateMemory();

        //避免初始化
        A obj = (A) u.allocateInstance(A.class);
        obj.print();
    }

    class A {
        private int a;

        public A() {
            a = 10; // initialization
        }

        public void print() {
            System.out.println(a);
        }
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


    /**
     * Unsafe类直接操作内存
     */
    public static void operateMemory() {
        Unsafe unsafe = UnsafeUtil.getUnsafe();

        /**
         * Report the size in bytes of a native pointer, as stored via {@link
         * #putAddress}.  This value will be either 4 or 8.  Note that the sizes of
         * other primitive types (as stored in native memory blocks) is determined
         * fully by their information content.
         */
        System.out.println("addressSize:" + unsafe.addressSize());
        System.out.println("pageSize:" + unsafe.pageSize());
    }
}
