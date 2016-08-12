package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/12.
 * <p/>
 * 参考:http://www.javaworld.com/article/2074481/java-concurrency/java-101--understanding-java-threads--part-4---thread-groups--volatility--and-threa.html
 * <p/>
 * 线程组的目的是为了降低对线程管理的复杂度。
 * 线程组由一组线程组成，初次以外，它还可以包含其他的线程组。
 * <p/>
 * <p/>
 * system
 * |
 * |--- system threads
 * |
 * main
 * |
 * |
 * |
 * --------------------------------------
 * |      |                 |
 * -----------    -----------
 * main thread  |subGroup1|    |subGroup2|
 * |      |         |
 * thread1   thread2    my thread
 */
public class ThreadGroupTest {
    public static void main(String[] args) {

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println("current:" + threadGroup.getName() + ",parent:" + threadGroup.getParent().getName());
        ThreadGroup parent = threadGroup.getParent();
        System.out.println(parent.getParent());

        //获取system线程组以及其子线程中的所有线程
        Thread[] threads = new Thread[parent.activeCount()];
        int enumerate = parent.enumerate(threads, true);
        System.out.println("number is : " + enumerate);
        for (Thread thread : threads) {
            System.out.println(thread);
        }
        System.out.println("----------------------------------");


        /*
         * 下面通过创建线程组构建成上面线程组继承结构
         * To prove that the subgroup 1 and subgroup 2 groups are main's only subgroups
         */
        ThreadGroup tg = new ThreadGroup("subgroup 1");
        Thread t1 = new Thread(tg, "thread 1");
        Thread t2 = new Thread(tg, "thread 2");
        Thread t3 = new Thread(tg, "thread 3");

        tg = new ThreadGroup("subgroup 2");
        Thread t4 = new Thread(tg, "my thread");

        //获取当前线程的线程组,即main的线程组
        tg = Thread.currentThread().getThreadGroup();
        //从main线程组中返回其包含活跃线程组数量，该值时一个预估值
        int agc = tg.activeGroupCount();
        System.out.println("Active thread groups in " + tg.getName() + " thread group: " + agc);

        //将线程组的信息输出,该方法适合于调式
        tg.list();

    }
}
