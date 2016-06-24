package com.panlingxiao.concurrency.synchronizer.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by panlingxiao on 2016/6/22.
 */
public class SemaphoreTest {


    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(10);
    }
}
