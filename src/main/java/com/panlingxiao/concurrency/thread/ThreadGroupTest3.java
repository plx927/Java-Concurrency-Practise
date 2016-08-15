package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/15.
 * 在销毁线程组之后再去启动线程产生的错误。
 */
public class ThreadGroupTest3 {

    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("myGroup");
        Thread t = new Thread(group, new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        });
        System.out.println("activeCount:"+group.activeCount());
        group.destroy();
        t.start();
    }
}
