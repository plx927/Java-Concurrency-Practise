package com.panlingxiao.concurrency.synchronizer;

/**
 * Created by panlingxiao on 2016/7/30.
 */
public class VolatileTest {

   /*
    * 1.防止指令重排序
    * 2.保证内存可见性
    */
    static boolean flag = true;
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new MyThread();
        thread.start();
        //睡眠100毫秒,确保线程已经启动
        Thread.sleep(100);
        flag = false;
    }

    public static class MyThread extends Thread{
        @Override
        public   void run() {
            while(flag){
                /*
                 * 注意:这里如果打印输出，无法得到预期效果。线程会结束执行。
                 * 其原因是println方法使用了内部使用了synchronized关键字,
                 * synchronized除了可以做到互斥锁外，还可以保存内存的一致性问题，它会直接从主存中获取数据，
                 * 而不会从线程的缓存中读取数据。
                 */
              //  System.out.println("aaa");
            }
        }
    }
}
