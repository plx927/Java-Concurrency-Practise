package com.panlingxiao.concurrency.format;

import java.text.SimpleDateFormat;

/**
 * Created by panlingxiao on 2016/8/3.
 */
public class SimpleDateFormatTest {
    public static void main(String[] args) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String[] dates = {"2016-09-01","2016-09-02","2016-09-03"};
        Thread[] threads = new Thread[dates.length];
        for(int i = 0  ;i < threads.length;i++){
            final int j = i;
            //创建线程
            threads[i] = new Thread(){
                @Override
                public void run() {
                    try {
                        simpleDateFormat.parse(dates[j]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            threads[i].start();
        }
    }
}
