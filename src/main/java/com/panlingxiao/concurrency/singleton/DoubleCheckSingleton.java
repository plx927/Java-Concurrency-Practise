package com.panlingxiao.concurrency.singleton;

/**
 * Created by panlingxiao on 2016/8/3.
 */
public class DoubleCheckSingleton {


    private static DoubleCheckSingleton singleton ;
    private DoubleCheckSingleton(){
    }

    /**
     *
     * @return
     */
    public static  synchronized  DoubleCheckSingleton getInstance(){
        if(singleton == null){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            singleton = new DoubleCheckSingleton();
        }
        return singleton;
    }


    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                System.out.println(DoubleCheckSingleton.getInstance());
            }
        }.start();


        new Thread(){
            @Override
            public void run() {
                System.out.println(DoubleCheckSingleton.getInstance());
            }
        }.start();

    }


}
