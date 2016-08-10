package com.panlingxiao.concurrency.design_pattern.future;

/**
 * Created by panlingxiao on 2016/8/11.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        Data data = client.call("hello world");
        client.doOtherSomething();
        String result = data.getResult();
        System.out.println("得到处理结果:"+result);
    }
}
