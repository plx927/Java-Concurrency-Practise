package com.panlingxiao.concurrency.lock;

/**
 * Created by panlingxiao on 2016/7/31.
 * 诡异且错误的加锁
 */
public class BadLockOnInteger implements Runnable{
    public static Integer number = 0;

    @Override
    public void run() {
        for(int i = 0;i<1000000;i++){
            /**
             * 当线程获取锁之后,不应该去修改锁对象
             * 造成数据不一致的原因:
             * 假设线程1和线程2同时去锁(i=129)这个对象，线程1获取到锁，此时线程2等待129这把锁。
             * 当线程1释放了129，此时变量的i的值已经为130了，但是线程2获取到的是129对象的锁，修改的值却是130，而此时线程1也在改130.
             */
            synchronized (number){
                number++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final BadLockOnInteger badLockOnInteger = new BadLockOnInteger();
        Thread thread1 = new Thread(badLockOnInteger);
        Thread thread2 = new Thread(badLockOnInteger);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(BadLockOnInteger.number);
    }
}
