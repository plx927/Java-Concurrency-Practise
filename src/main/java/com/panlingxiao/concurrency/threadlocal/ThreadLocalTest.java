package com.panlingxiao.concurrency.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by panlingxiao on 2016/7/22.
 */
public class ThreadLocalTest {



    public static void main(String[] args) {

    }


    static class ThreadID{
        private static  AtomicInteger counter = new AtomicInteger();
        private  static  ThreadLocal<Integer> threadId = new ThreadLocal<Integer>(){
           @Override
           protected Integer initialValue() {
               return counter.incrementAndGet();
           }
       };

        public Integer get(){
            return threadId.get();
        }

    }
}

