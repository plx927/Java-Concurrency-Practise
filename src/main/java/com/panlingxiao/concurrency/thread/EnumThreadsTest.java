package com.panlingxiao.concurrency.thread;

/**
 * Created by panlingxiao on 2016/8/15.
 */
public class EnumThreadsTest {

    public static void main (String [] args)
    {
        // 查询system线程组
        ThreadGroup system = null;
        ThreadGroup tg = Thread.currentThread ().getThreadGroup ();
        while (tg != null)
        {
            system = tg;
            tg = tg.getParent ();
        }
        // Display a list of all system and application threads, and their
        // daemon status
        if (system != null)
        {
            //获取当前线程组以及其子线程组中的活跃线程数
            Thread [] thds = new Thread [system.activeCount ()];
            int nthds = system.enumerate (thds);
            for (int i = 0; i < nthds; i++)
                System.out.println (thds [i] + " " + thds [i].isDaemon ());
        }
    }
}
