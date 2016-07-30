package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/7/30.
 * 通过StackOverflowError证明方法的执行是基于入栈操作的
 * Java虚拟机的栈可以细分为:本地方法栈和虚拟机栈
 * 本地方法栈是执行Native方法，虚拟机栈是执行普通方法。
 */
public class JVMTheadTest {

    public static int stackSize = 0;
    public static void main(String[] args) {
        try {
            deadRecursive();
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("maxStackSize : "+stackSize);
        }

    }

    /*
     * 没有出口的递归
     */
    public static void  deadRecursive(){
        stackSize++;
        deadRecursive();
    }

}


