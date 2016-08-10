package com.panlingxiao.concurrency.collection.copyonwrite;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by panlingxiao on 2016/8/10.
 * 并发读写
 */
public class CopyOnWriteArrayListTest2 {

    public static void main(String[] args) {
       final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
        list.add("a");

        //写线程
        new Thread(){
            @Override
            public void run() {
                list.add("b");
                list.add("c");
            }
        }.start();

        //读线程
        new Thread(){
            @Override
            public void run() {
               for(Iterator<String> iterator = list.iterator(); iterator.hasNext();){
                   String str = iterator.next();
                   System.out.println(str);
               }
            }
        }.start();





    }


}
