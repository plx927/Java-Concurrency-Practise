package com.panlingxiao.concurrency.singleton;

/**
 * Created by panlingxiao on 2016/8/3.
 *
 * 初始化是原子操作，原子操作就不会被并发执行。
 * 直接将原子操作交给JVM来处理。
 */
public class InnerClassSingleton {

    private  static class Singleton{

        static{
            System.out.println("before construct Singleton");//2
        }

        private static Singleton singleton = new Singleton();

        static{
            System.out.println("after construct Singleton"); //4
        }

        private Singleton(){
            System.out.println("construct Singleton"); //3
        }
    }

    public static Singleton getInstance(){
      //  System.out.println("return Singleton");
        //访问内部类的静态属性，此时是对类的主动使用，因此会初始化内部类
        return Singleton.singleton;
    }


    static {
        System.out.println("Init InnerClassSingleton"); //1
    }

    //Java虚拟机启动时被标明为启动类,此时就会初始化InnerClassSingleton
    public static void main(String[] args) {
        InnerClassSingleton.getInstance();
    }

}
