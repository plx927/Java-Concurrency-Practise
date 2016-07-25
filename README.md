# Java-Concurrency-Practise

>个人并发编程练习以及总结

### 同步工具类

#### ReentrantLock 

#### CylicBarrier
Barrier为"栅栏"的意思,在此表示阻塞线程继续执行，让线程进入到等待状态(TIMED_WAITING)。 
当所有线程都到达"栅栏"点时，栅栏就会倒下(tripped),此时会由最后一个线程执行"栅栏"中的Barrier Action, 在执行之后,所有的线程就能继续往下执行。
在CyclicBarrier中，每一次使用CylicBarrier都使用一个Generation来表示,在使用完成之后,会生成一个新的Generation来表示下一次的使用。



#### CountDownLatch


#### AbstractQueuedSynchronizer

等待队列
AQS的等待队列时CLH锁队列的变种。CLH锁通常用于自旋锁。
在AQS的的等待队列中，每一个节点的status字段用于维系追踪一个线程是否应该被阻塞。一个节点会被通知当其前驱节点被释放时。
队列中的每个节点作为"特定通知风格"的监视器，它只有唯一的等待线程。
节点的状态字段无法用于控制线程是否被授权锁。当一个线程第一次进入队列时，它会尝试去获取，如果第一次无法获取成功，那么它只能被给与重新竞争的权力。


#### ThreadLocal
每一个线程内部都维护着一个ThreadLocalMap，ThreadLocalMap是一个定制化的HashMap，其key为ThreadLocal,value是绑定到线程中的值。
在ThreadLocalMap中对值进行查询是通过ThreadLocal底层所维护的threadLocalHashCode来完成的。