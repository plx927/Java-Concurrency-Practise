# Java多线程基础篇

## 1、理解并发(Concurrent)和并行(Parallel)的区别
- http://ifeve.com/parallel_and_con/
- http://www.cnblogs.com/yangecnu/p/3164167.html

## 2、线程的创建及执行原理分析

### 2.1 继承Thread类
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


### 2.2 实现Runnable接口
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

### 2.3 通过FutureTask和Callable
在JDK1.5之前，由于Runnable接口的run()方法被设计成了无返回值，因此线程在执行完成后都无法将执行结果以返回值的形式返回。
从1.5开始，在并发包中引入了Callable接口,Callable被设计成一个具有返回值并且可以抛出异常的执行任务。但是由于Thread类的
执行逻辑必须存在于run()方法，我们所定义了Callable的实现类是无法被Thread所执行的。为了解决这个类型不兼容的问题，FutureTask出现了。
FutureTask自身就是一个Runnable接口的实现类，其作用是可以将用户所设计的Callable接口的实现类包装成Runnable来交给Thread来执行。
在这里，暂时先不对FutureTask做更多细致的分析，首先了解其简单的使用，后面将会对Future以及FutureTask的源码做详细的分析。

参考代码：com.panlingxiao.concurrency.thread.CreateThreadTest3


### 2.4 初学者容器犯的错误
- 使用run方法来启动线程
- 多次调用Thread的start()方法


### 2.5 TimeUnit以及Timer类分析及使用


### 2.6 Thread类源码阅读及线程启动原理分析



## 3、线程信息及线程状态



### 线程状态----Java线程的生命周期
参考:https://avaldes.com/java-thread-states-life-cycle-of-java-threads/



### 线程中断





## 4、ThreadGroup
当我们在编写网络应用程序时(使用传统的Blocking-IO)，通常情况下我们通过一个线程去等待接收客户端的请求，获取到请求后就去处理相应的处理，比如数据库事务或者更加复杂的计算等等。
用于等待接收客户端请求线程会创建一个新的线程来处理客户端的请求。当客户端的请求越多，此时就会有大量的线程产生，因此我们的应用程序对于这些线程将会变得难以管理。而为了简化
对于线程的管理，应用程序可以将这些处理客户端请求的线程放入到一个线程组中来进行统一管理，因此线程组的目的是为了降低对线程管理的复杂度，在Java中提供了`java.lang.ThreadGroup`来表示线程组。
线程组代理了一组线程的集合，线程组除了可以包含线程意外，它还可以包含其他的线程组(即子线程组)。

随着应用程序的复杂以及线程、线程组的增多，线程组之间最终会形成一个树状结构，下面就来分析一下JDK中的线程组:

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
在上图的结构中，最顶层是system线程组。JVM创建system线程组，将GC线程和其他的系统任务线程添加到system线程组中。system线程组作为应用线程组层级结构中的根线程组，system线程组没有parent。
在system线程组的下面是main线程组，它是system线程组的子线程组。main线程组中至少包含一个线程，JVM会创建主线程，将其添加到main线程组中来执行main()方法中的字节码指令。
在main线程中除了包含main线程外，还包含其他另外的两个线程组subGroup1和subGroup2。这两个线程组都是由应用本身所创建，并且subGroup1中包含Thread1和Thread2 两个线程，而subGroup2中只包含
my thread一个线程。
在了解了上面的基本概念后，下面让我们开始学习线程组的基本使用。

### 4.1 ThreadGroup基本使用
ThreadGroup中提供了两个构造public的构造方法:ThreadGroup(String name) and ThreadGroup(ThreadGroup parent, String name)。这两个构造方法在使用时都需要指定构造方法的名字来作为线程组的标示。
这两个方法的不同之处在于在创建线程组时是否为其显示指定父线程组。对于每一个线程组，除了system线程组以外，其他线程都**必须有一个父线程组**。对于使用ThreadGroup(String name)方法所创建的线程组，它的parent
是调用ThreadGroup(String name)方法的线程所在的线程组。比如我们在main方法中调用ThreadGroup(String name)方法，那么新建的线程组的parent就是main线程组。对于ThreadGroup(ThreadGroup parent, String name)
方法而言，我们在创建的需要自己指定该线程组的parent到底是哪个。

下面的代码片段显示如何创建线程组:

```
    ThreadGroup tg1 = new ThreadGroup("tg1");
    ThreadGroup tg2 = new ThreadGroup(tg1,"tg2");
```

参考代码:com.panlingxiao.concurrency.thread.ThreadGroupTest

### 4.2 中断线程组
ThreadGroup的interrupt()方法允许一个线程去中断指定的一个线程组中的线程以及该线程组所包含的子线程组。这个技术可以适用于如下的应用场景:我们应用的main线程创建了多个线程，每一个线程
都执行整体任务的部分单元。其中任何线程想要检查执行结果的前提必须是所有线程必须完成它们各自的任务后，因此每个线程在完成它们的自己的任务后必须先等待。(这种场景也可以使用CyclicBarrier来处理)
main线程监控着整个任务的工作状态，一旦所有的线程都处于等待的状态，那么main线程就调用线程组中的线程来中断其所包含的线程的等待，此时这些线程可以继续对执行结果进行检查并且处理。
参考代码：com.panlingxiao.concurrency.thread.ThreadGroupInterruptTest


### 4.3 获取线程组中的线程
我们可以使用ThreadGroup的activeCount()和enumerate(Thread[] tarray)来枚举出

参考文章:

- http://www.javaworld.com/article/2074481/java-concurrency/java-101--understanding-java-threads--part-4---thread-groups--volatility--and-threa.html
 
 
### 4.4 实现一个简单的线程池
 