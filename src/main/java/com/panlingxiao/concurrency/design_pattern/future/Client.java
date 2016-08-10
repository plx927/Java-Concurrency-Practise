package com.panlingxiao.concurrency.design_pattern.future;

/**
 * Created by panlingxiao on 2016/8/11.
 */
public class Client {

    public Data call(final String param){
        final FutureData futureData = new FutureData();
        /*
         * 启动线程异步进行耗时的处理
         */
        new Thread(){
            @Override
            public void run() {
                //执行真正的耗时处理
                RealData realData = new RealData(param);
                futureData.setRealData(realData);
            }
        }.start();
        return futureData;
    }

    public void doOtherSomething()throws InterruptedException{
        System.out.println("客户端开始执行其他操作");
        Thread.sleep(4000);
        System.out.println("客户端结束执行其他操作");
    }
}
