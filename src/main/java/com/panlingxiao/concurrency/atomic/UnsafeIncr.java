package com.panlingxiao.concurrency.atomic;

/**
 * Created by panlingxiao on 2016/8/1.
 * 测试JDK中的"++"运算符的非原子性操作
 * 启动100个线程，每个线程对counter加10000次，那么理论结果应该是1000000，但是最终得到的结果却不是。
 * 因此证明"++"是非线程安全的操作，同时可以通过 javap 来查看生成class文件,观察字节码指令。
 */
public class UnsafeIncr {

    static int counter = 0;
    public static void main(String[] args) throws InterruptedException {
        final int THREADS_NUM = 100;
        final int INCR_TIME = 10000;
        Thread[] threads = new Thread[THREADS_NUM];
        for(int i =  0;i < threads.length;i++){
            threads[i] = new Thread(){
                @Override
                public void run() {
                    for(int j = 0;j < INCR_TIME;j++){
                        counter++;
                    }
                }
            };
            threads[i].start();
        }
        for(Thread thread : threads){
            thread.join();
        }

        System.out.println(String.format("counter is %d",counter));
    }

}
