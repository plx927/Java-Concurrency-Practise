package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/6.
 */
public class ThreadThrowExceptionTest {

    public static void main(String[] args) {
        final boolean flag = args.length == 0;

        Thread t1 = new Thread(){
            @Override
            public void run() {
                while (true){
                    if(!flag){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        throw  new RuntimeException();
                    }
                }
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                for(int i = 0;i < 10000;i++){
                    if(i == 9999){
                        throw  new RuntimeException();
                    }
                }
            }
        };

        t1.start();
        t2.start();
    }
}
