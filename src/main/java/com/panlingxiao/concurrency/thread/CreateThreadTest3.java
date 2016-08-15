package com.panlingxiao.concurrency.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by panlingxiao on 2016/8/14.
 * 通过使用FutureTask和Callable的方式创建线程，该方式是在JDK1.5之后才能使用。
 * 并且使用该方式创建线程后，在线程执行完毕后可以返回一个执行结果。
 */
public class CreateThreadTest3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum = 0 ;
                for(int i = 1;i <= 100;i++){
                    sum += i;
                }
                return sum;
            }
        });

        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("线程开始执行");
        System.out.println("执行结果:"+futureTask.get());
    }
}
