package com.panlingxiao.concurrency.visablity;

/**
 * Created by panlingxiao on 2016/7/31.
 */
public class DirtyRead {
    private String  value1 = "hello";
    private String value2 = "world";

    public void setValue(String value1,String value2) throws InterruptedException {
        this.value1 = value1;
        Thread.sleep(1000);
        this.value2 = value2;
        System.out.println(String.format("setValue invoked is %s,value2 is %s",value1,value2));
    }


    public void getValue(){
        System.out.println(String.format("getValue invoked is %s,value2 is %s",value1,value2));
    }

    public static void main(String[] args) throws InterruptedException {
        final DirtyRead dirtyRead = new DirtyRead();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    dirtyRead.setValue("a","b");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        Thread.sleep(500);
        /*
         * 由于setValue方法没有执行完毕，而主线程尝试去读取数据，因此造成的数据读取到出现了不一致性。
         */
        dirtyRead.getValue();
    }
}
