


### Volatile
Volatile应用在这样的场景下：当一个线程修改了共享的属性变量值，另外一个线程可以看见其修改的变化。在正常情况下，我们总是期望其他线程可以一直读取到一个共享变量的最新值，然后在实际并非如此。
基于性能原因的考虑，Java并不要求JVM的实现对于一个共享的变量的读写操作必须在**主存(main memory)或者对象堆内存(object heap memory)**中进行。与之相反的是，JVM通常是从**寄存器
(processor register)或者高速缓存(cache)**中读取共享变量的值，这些区域通常也被称之为**工作内存(working memory)**;类似地,JVM通常也不会只会在**寄存器或者高速缓存**中对变量
进行修改，而这正是影响共享变量对于线程可见性的原因。

假设一个程序创建了一个Integer类型的**共享变量**x,并且其在主存中的初始值为10。此时程序启动两个线程，一个线程去修改x的值，而另一个线程去读取x的值。而JVM会为每一个运行的线程单独地分配其私有的工作内存，
这意味着在每一个线程的工作内存中，到会有一份其私有的x变量的拷贝。当写线程将x的值修改为6，这个线程只是仅仅修改其工作内存中的值而已，并不会去修改主存中变量x的值；同样的，当读线程读取x的值时，它也仅仅只是
从其工作内存中读取，因此读线程最终读取到的结果依旧是10(因为线程在最初的使用变量时候会从主存中获取到共享变量的拷贝值)。最终的结果是导致读线程没有意识到写线程修改了共享变量的值。
如果一个线程无法观察到另外的一个线程更新了共享变量，那么将会引发一些列的问题。最典型的例子就是两个线程共享一个boolean类型的变量，读线程(T1)根据这个值在不停的轮询，直到写线程(T2)修改了值之后才跳出循环；而如果不使用
volatile关键字修饰共享变量，即使写线程(T2)修改了变量的值，读线程(T1)依旧无法获取到最新的值，从而导致一直处在一个循环中。

参考代码:com.panlingxiao.concurrency.visablity.VolatileTest

> 注意：volatile关键字不能与final关键字共用，否则编译出错。


当线程使用synchronized时，再去访问共享变量，此时不会发生内存可见性问题。其原因是当一个线程获取到锁后，该线程的工作内存会重新从主存中加载共享变量的值，与之类似的是，当一个线程释放了锁之后，工作内存中对于共享变量的拷贝
的改变会重新写入到主存中去。因此，在使用了synchronized后，我们对于共享变量的读写就没有必要使用volatile来进行修饰。

一些初学者可能会觉得可以使用volatile关键字来替代synchronized，然而并非如此。**Synchronized可以让我们的代码将几个操作放入到一个不可分割的单元中进行执行**，而volatile却做不到这一点。但是由于volatile在性能上要
高于synchronized,当多个线程需要使用一个共享变量进行通信时，我们应该优先选取volatile来进行处理。

参考文章：

- http://www.javaworld.com/article/2074481/java-concurrency/java-101--understanding-java-threads--part-4---thread-groups--volatility--and-threa.html?page=2

### Unsafe

参考文章：
- http://mishadoff.com/blog/java-magic-part-4-sun-dot-misc-dot-unsafe/
- http://ifeve.com/sun-misc-unsafe/