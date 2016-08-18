package com.panlingxiao.concurrency.atomic;

/**
 * Created by panlingxiao on 2016/8/18.
 */
public interface Counter {
    void increment();
    long getCounter();
}
