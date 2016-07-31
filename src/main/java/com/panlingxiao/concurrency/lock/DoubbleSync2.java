package com.panlingxiao.concurrency.lock;

/**
 * Created by panlingxiao on 2016/7/31.
 */
public class DoubbleSync2 {

    public static class Parent{

        public synchronized void method1(){
            System.out.println("method1");
        }
    }

    public static class Child extends Parent{
        public synchronized void method2() {
            System.out.println("method2");
            super.method1();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        final Child child = new Child();
        new Thread(){
            @Override
            public void run() {
                child.method2();
            }
        }.start();
    }
}
