package com.panlingxiao.concurrency.collection.unsafe;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by panlingxiao on 2016/8/1.
 * 通过Debug模拟并发添加元素到ArrayList中引发ArrayIndexOutOfBoundsException的原因
 */
public class ConcurrentAddItemToListCauseException {
    public static void main(String[] args) throws InterruptedException {
        final int THREAD_NUM = 15;
        final ArrayList<String> list = new ArrayList<String>();
        Thread[] threads = new Thread[THREAD_NUM];
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUM);
        for (int i = 0; i < threads.length; i++) {
            final int v = i;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                        list.add(String.valueOf(v));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(list.size());

    }
}
