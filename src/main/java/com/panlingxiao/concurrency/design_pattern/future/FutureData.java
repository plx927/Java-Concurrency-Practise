package com.panlingxiao.concurrency.design_pattern.future;

/**
 * Created by panlingxiao on 2016/8/11.
 * <p>
 * FutureData作为真实对象的代理快速返回给客户端
 */
public class FutureData implements Data {

    private RealData realData;
    private boolean isReady;

    /**
     * 返回真实对象的结果
     *
     * @return
     * @throws InterruptedException
     */
    @Override
    public synchronized String getResult() throws InterruptedException {
        //如果真实对象还未完成处理，则会让调用线程等待
        if (!isReady) {
            this.wait();
        }
        return realData.getResult();
    }

    public synchronized void setRealData(RealData realData) {
        this.realData = realData;
        isReady = true;
        this.notify();
    }
}
