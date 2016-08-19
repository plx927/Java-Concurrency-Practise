package com.panlingxiao.concurrency.visablity;

/**
 * Created by panlingxiao on 2016/8/19.
 * 分析volatile赋值的字节码
 */
public class VolatileTest2 {


    volatile  int i;

    int j;

    public void setVolatileVariable(int value){
        i = value;
    }

    public void setCommonVariable(int value){
        j = value;
    }

}
