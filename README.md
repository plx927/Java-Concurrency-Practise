# Java-Concurrency-Practise
> 个人并发编程练习以及总结

### 同步工具类

#### 1.CylicBarrier
Barrier为"栅栏"的意思,在此表示阻塞线程继续执行，让线程进入到等待状态(TIMED_WAITING)。
当所有线程都到达"栅栏"点时，栅栏就会倒下(tripped),此时会由最后一个线程执行"栅栏"中的Barrier Action,
在执行之后,所有的线程就能继续往下执行。

