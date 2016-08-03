package com.panlingxiao.concurrency.format;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by panlingxiao on 2016/8/3.
 * 测试SimpleDateFormat的线程非安全以及分析源码原因
 *
 */
public class TestUnsafeFormatter {

    public static void main(String[] args) throws InterruptedException {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final Date first = new Date();
        final Date second = new Date(first.getTime()+1000*60);
        final Date third = new Date(second.getTime()+1000*60);
        //3个时间各相差1分钟，但是格式化后的结果却是一样的。
        final Date[] dates = {first,second,third};

        final int THREAD_NUM = 3;
        Thread[] threads = new Thread[THREAD_NUM];
        for (int i = 0; i < threads.length; i++) {
            final int j = i;
            threads[i] = new Thread() {
                @Override
                public void run() {
                        String value = format.format(dates[j]);
                        System.out.println(getName() + ":" + value);
                }
            };
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

    }
}
