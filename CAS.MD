


### Volatile
Volatile应用在这样的场景下：当一个线程修改了共享的属性变量值，另外一个线程可以看见修改的变化。在正常情况下，我们总是期望其他线程可以一直读取到一个共享变量的最新值，然后在实际并非如此。
基于性能原因的考虑，Java并不要求JVM的实现对于一个共享的变量的读写操作必须在**主存(main memory)或者对象堆内存(object heap memory)**中进行。与之相反的是，JVM通常是从**寄存器
(processor register)或者高速缓存(cache)**中读取共享变量的值，这些区域通常也被称之为**工作内存(working memory)**;类似地,JVM通常也不会只会在**寄存器或者高速缓存**中对变量
进行修改，而这正是影响共享变量对于线程可见性的原因。


参考文章：

- http://www.javaworld.com/article/2074481/java-concurrency/java-101--understanding-java-threads--part-4---thread-groups--volatility--and-threa.html?page=2

