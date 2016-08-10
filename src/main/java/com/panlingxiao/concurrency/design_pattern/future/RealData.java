package com.panlingxiao.concurrency.design_pattern.future;

/**
 * Created by panlingxiao on 2016/8/11.
 */
public class RealData implements Data{

    private String result;

    public RealData(String param){
        try {
            Thread.sleep(5000);
            this.result = "RealData:"+param;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getResult() {
        return result;
    }
}
