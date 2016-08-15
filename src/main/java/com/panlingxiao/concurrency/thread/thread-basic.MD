# Java多线程基础篇

## 理解并发(Concurrent)和并行(Parallel)的区别
- http://ifeve.com/parallel_and_con/
- http://www.cnblogs.com/yangecnu/p/3164167.html

## 线程的创建及执行原理分析

### 继承Thread类
第一种创建的方式是通过继承Thread类，重写Thread类的run()方法来完成线程的创建，代码如下:
```
class MyThread extends Thread{
 
 @Override
 public void run(){
 
    executeSomething();
 }
}

public static void main(String[]){
  MyThread thread = new MyThread();
  thread.start();
}

```
参考代码：com.panlingxiao.concurrency.thread.CreateThreadTest


### 实现Runnable接口
第二中创建线程的是通过实现Runnable接口，将Runnable接口的实现类的实例作为参数传递给Thread对象。
```
    public MyTask implements Runnable{
        public void run(){
            doSomething();
        }
    }
    
    public static void main(String[] args){
    
        //作为参数传递给Thread来执行run方法中的代码
        Thread t = new Thread(new MyTask());
        t.start();
    }
        
```
参考代码：com.panlingxiao.concurrency.thread.CreateThreadTest2

### 通过FutureTask和Callable
在JDK1.5之前，由于Runnable接口的run()方法被设计成了无返回值，因此线程在执行完成后都无法将执行结果以返回值的形式返回。
从1.5开始，在并发包中引入了Callable接口,Callable被设计成一个具有返回值并且可以抛出异常的执行任务。但是由于Thread类的
执行逻辑必须存在于run()方法，我们所定义了Callable的实现类是无法被Thread所执行的。为了解决这个类型不兼容的问题，FutureTask出现了。
FutureTask自身就是一个Runnable接口的实现类，其作用是可以将用户所设计的Callable接口的实现类包装成Runnable来交给Thread来执行。
在这里，暂时先不对FutureTask做更多细致的分析，首先了解其简单的使用，后面将会对Future以及FutureTask的源码做详细的分析。
参考代码：com.panlingxiao.concurrency.thread.CreateThreadTest3


## 线程信息及线程状态



### 线程状态----Java线程的生命周期
参考:https://avaldes.com/java-thread-states-life-cycle-of-java-threads/







## ThreadGroup
线程组的目的是为了降低对线程管理的复杂度，在Java中提供了`java.lang.ThreadGroup`来表示线程组。线程组由一组线程组成，初次以外，它还可以包含其他的线程组。

                           system
 
                             |--- system threads
                             |
                            main
                             |
                             |
                             |
              --------------------------------------
                  |      |                 |
                       -----------    -----------
          main thread  |subGroup1|    |subGroup2|
                         |      |         |
                    thread1   thread2    my thread
在上图的结构中，最顶层是system线程组。JVM创建系统线程组，将GC线程和其他的系统任务线程添加到system线程组中。system线程组作为应用线程组层级结构中的根线程组，system线程组没有parent。
在system线程组的下面是main线程组，它是system线程组的子线程组。main线程组中至少包含一个线程，JVM会创建主线程，将其添加到main线程组中来执行main()方法中的字节码指令。
在main线程中除了包含main线程外，还包含其他另外的两个线程组subGroup1和subGroup2。这两个线程组都是由应用本身所创建，并且subGroup1中包含Thread1和Thread2 两个线程，而subGroup2中只包含
my thread一个线程。
在了解了上面的基本概念后，下面让我们开始学习线程组的基本使用。

### ThreadGroup基本使用
ThreadGroup中提供了两个构造public的构造方法:ThreadGroup(String name) and ThreadGroup(ThreadGroup parent, String name)。这两个构造方法在使用时都需要指定构造方法的名字来作为线程组的标示。
这两个方法的不同之处在于在创建线程组时是否为其显示指定父线程组。对于每一个线程组，除了system线程组以外，其他线程都**必须有一个父线程组**。对于使用ThreadGroup(String name)方法所创建的线程组，它的parent
是调用ThreadGroup(String name)方法的线程所在的线程组。比如我们在main方法中调用ThreadGroup(String name)方法，那么新建的线程组的parent就是main线程组。对于ThreadGroup(ThreadGroup parent, String name)
方法而言，我们在创建的需要自己指定该线程组的parent到底是哪个。

下面的代码片段显示如何创建线程组:

```
    ThreadGroup tg1 = new ThreadGroup("tg1");
    ThreadGroup tg2 = new ThreadGroup(tg1,"tg2");
```



### 中断线程组



参考:
http://www.javaworld.com/article/2074481/java-concurrency/java-101--understanding-java-threads--part-4---thread-groups--volatility--and-threa.html
 