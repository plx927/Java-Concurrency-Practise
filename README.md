# Java-Concurrency-Practise

>个人并发编程练习以及总结

### [1、Thread 基础]()


### 源码分析start方法的本质以及分析两种实现方式的比较

```
    
 public synchronized void start() {
        /**
         * This method is not invoked for the main method thread or "system"
         * group threads created/set up by the VM. Any new functionality added
         * to this method in the future may have to also be added to the VM.
         *
         * A zero status value corresponds to state "NEW".
         */
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        /* Notify the group that this thread is about to be started
         * so that it can be added to the group's list of threads
         * and the group's unstarted count can be decremented. */
        group.add(this);

        boolean started = false;
        try {
            //调用start方法，最终会调用start0方法。
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                /* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack */
            }
        }
    }

```

通过上面的代码可以看到，用户调用线程的start方法，其本质是调用了native的start0()方法，start0()将*当前对象以一个线程的方式启动并且自动调用run方法来进行执行*。
*Thread对象的start方法只能被调用一次，如果多次调用，会抛出IllegalThreadStateException。线程一旦死亡，线程就无法再次重新启动。*

*思考:为什么要对start方法加上synchronized关键字?*

```
  @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }

```

>当JVM启动的时候，通常会启动一个非守护线程(主线程),它所执行的代码就是main()方法中的内容。当JVM遇到如下的情况会退出：

- 调用System.exit()方法来强制终止JVM执行。
- 所有的非守护线程死亡、所有的非守护线程结束run方法执行、所有的非守护线程在run方法中抛出异常并且没有被捕获时，在JVM也会退出执行。


### 线程中断(Thread Interrupt)
通过Thread#interrupt方法来中断线程的执行。如果interrupt不是当前线程，那么该方法底层会去检查其是否有执行权限，如果没有，会抛出一个SecurityException。
interrupt()方法的本质是设置当前线程的*中断标志位*。

(1).如果线程由于执行*wait、join、sleep*方法时而处于等待状态，那么线程的*中断标志位*会被清除，并且抛出一个InterruptedException。参考com.panlingxiao.concurrency.thread.ThreadInterrupt。

(2).如果线程阻塞在InterruptibleChannel的一个IO操作上，那么线程中断会引发Channel的关闭，线程的标志位会被清除，并且抛出ClosedByInterruptException。

(3).如果线程阻塞在Selector的select方法上，那么线程的标记位也会被清除，并且select()方法会立即返回，就好像其他线程调用Selector的wakeUp()方法。

如果不是上述分析的操作，那么线程的标记位就都会被设置，但是并不会影响线程继续执行；通过分析interrupt()方法，发现其底层是调用native的interrupt0()方法来完成中断标记位的设置。
```
    public void interrupt() {
        if (this != Thread.currentThread())
            checkAccess();

        synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                interrupt0();           // Just to set the interrupt flag
                b.interrupt(this);
                return;
            }
        }
        interrupt0();
    }
```


### Synchronized

常见问题:

- 对静态方法使用synchronized关键字，加锁的对象是谁?
- 实现线程安全单例的几种方式



### Notify & Wait



## 2、CAS 操作


## 3、锁

### Lock

### ReentrantLock

### ReentrantReadWriteLock

### Condition


## 4、同步工具类



### ReentrantLock 

### CyclicBarrier
Barrier为"栅栏"的意思,在此表示阻塞线程继续执行，让线程进入到等待状态(TIMED_WAITING)。 
当所有线程都到达"栅栏"点时，栅栏就会倒下(tripped),此时会由最后一个线程执行"栅栏"中的Barrier Action, 在执行之后,所有的线程就能继续往下执行。
在CyclicBarrier中，每一次使用CylicBarrier都使用一个Generation来表示,在使用完成之后,会生成一个新的Generation来表示下一次的使用。



### CountDownLatch

### Semaphore


### AbstractQueuedSynchronizer
AQS通过其内部的一个**先进先出的等待队列**，为**阻塞锁**和**同步器**提供了一个框架的实现。
这些同步器的有一个特点：它们通过一个唯一原子int型可以变量来表示其状态。
子类必须定义AQS中所提供的protected的方法来去改变状态，根据同步器被线程获取或者释放来定义状态的含义。
AQS通过子类给定的状态信息，来执行所有入队列和阻塞操作。(从这点可以看出这是一个框架实现)
AQS的获取操作是一种*依赖状态的操作*，并且通常会阻塞。


等待队列
AQS的等待队列时CLH锁队列的变种。CLH锁通常用于自旋锁。
在AQS的的等待队列中，每一个节点的status字段用于维系追踪一个线程是否应该被阻塞。一个节点会被通知当其前驱节点被释放时。
队列中的每个节点作为"特定通知风格"的监视器，它只有唯一的等待线程。
节点的状态字段无法用于控制线程是否被授权锁。当一个线程第一次进入队列时，它会尝试去获取，如果第一次无法获取成功，那么它只能被给与重新竞争的权力。

## 5、并发集合

### Queue


### ArrayBlockingQueue

### LinkedBlockingQueue

### ConcurrentLinkedQueue

##### ConcurrentLinkedQueue分析



##### ConcurrentLinkedQueue的size()问题:

##### 参考:
(1). http://www.cs.rochester.edu/u/michael/PODC96.html

(2). http://www.infoq.com/cn/articles/ConcurrentLinkedQueue

(3). http://www.ibm.com/developerworks/cn/java/j-lo-concurrent/index.html

(4). http://www.cnblogs.com/linjiqin/archive/2013/05/30/3108188.html





### CopyOnWriteArrayList

### ConcurrentSkipListMap

### ConcurrentHashMap


## 6.线程池
http://www.infoq.com/cn/articles/the-importance-of-thread-pool-in-java-web-application?utm_campaign=rightbar_v2&utm_source=infoq&utm_medium=articles_link&utm_content=link_text


## 7.ThreadLocal
每一个线程内部都维护着一个ThreadLocalMap，ThreadLocalMap是一个定制化的HashMap，其key为ThreadLocal,value是绑定到线程中的值。
在ThreadLocalMap中对值进行查询是通过ThreadLocal底层所维护的threadLocalHashCode来完成的。



## 8.多线程之设计模式

### Future模式




### 锁竞争问题
参考: http://www.ibm.com/developerworks/cn/java/j-lo-lock/#icomments



### 思考练习

http://www.iteye.com/topic/711162