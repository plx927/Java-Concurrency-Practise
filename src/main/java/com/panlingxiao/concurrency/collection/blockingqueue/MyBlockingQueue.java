package com.panlingxiao.concurrency.collection.blockingqueue;

import java.util.Random;

/**
 * Created by panlingxiao on 2016/8/4.
 * 自己使用notify和wait实现一个阻塞队列,这里模拟一个ArrayBlockingQueue队列
 * 底层使用数组来进行存储,并且对队列的大小做了严格限制。
 */
public class MyBlockingQueue<E> {


    //存储元素的数组
    private final E[] elements;

    //当前队列的大小
    private volatile int size = 0;

    private Object lock = new Object();

    private int takeIndex = 0;

    private int putIndex = 0;


    public MyBlockingQueue(int capacity) {
        //初始化队列最大所能存储的元素个数
        elements = (E[]) new Object[capacity];
    }


    public boolean add(E e) {
        if (offer(e)) {
            return true;
        }
        throw new IllegalStateException("Full Queue");
    }


    public boolean offer(E e) {
        checkNotNull(e);
        synchronized (lock) {
            if (size == elements.length) {
                return false;
            }
            putIndex = putIndex == elements.length ? 0 : putIndex;
            elements[putIndex++] = e;
            size++;
            return true;
        }
    }

    public void put(E e) throws InterruptedException {
        checkNotNull(e);
        synchronized (lock) {
            while (size == elements.length) {
                lock.wait();
            }
            putIndex = putIndex == elements.length ? 0 : putIndex;
            elements[putIndex++] = e;
            size++;
            lock.notify();
        }
    }


    public E take() throws InterruptedException {
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
            }
            takeIndex = takeIndex == elements.length ? 0 : takeIndex;
            E e = elements[takeIndex++];
            size--;
            lock.notify();
            return e;
        }
    }

    public E peek() {
        synchronized (lock) {
            if (size == 0) {
                return null;
            }
            takeIndex = takeIndex == elements.length ? 0 : takeIndex;
            return elements[takeIndex];
        }
    }

    public E poll() {
        synchronized (lock) {
            if (size == 0) {
                return null;
            }
            takeIndex = takeIndex == elements.length ? 0 : takeIndex;
            E e = elements[takeIndex++];
            size--;
            return e;
        }
    }


    void checkNotNull(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
    }


    public int size(){
        return this.size;
    }

    public static void main(String[] args) throws InterruptedException {
        final MyBlockingQueue<String> queue = new MyBlockingQueue<String>(1);
//        queue.offer("hello");
//        queue.offer("world");
//        queue.add("hello world");
//
//        boolean result = queue.offer("Python");
//        System.out.println("result:" + result);
//
//        System.out.println("size:" + queue.size());
//
//
//
//        String str = queue.take();
//        System.out.println(str);
//        System.out.println("size:" + queue.size());
//
//        str = queue.take();
//        System.out.println(str);
//        System.out.println("size:" + queue.size());
//
//
//        str = queue.poll();
//        System.out.println(str);
//        System.out.println("size:" + queue.size());
//
//
//        str = queue.poll();
//        System.out.println(str);
//        System.out.println("size:" + queue.size());



        //queue.put("Python");
        //queue.add("abc");

       Thread[] produceThread = new Thread[2];
        Thread[] consumeThread = new Thread[2];
        final String fruits[] = {"Apple","Pear","Banana"};
        for (int i = 0; i < produceThread.length; i++) {
            produceThread[i] = new Thread("生产者:" + i) {
               private Random random = new Random();
                @Override
                public void run() {
                    while (true) {
                        try {
                            int index = random.nextInt(3);
                            String fruit = fruits[index];
                            queue.put(fruit);
                            System.out.println(getName()+"生成了一个"+fruit+",当前队列大小:"+queue.size());
                            Thread.sleep((long)(Math.random()*300));
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            };
            produceThread[i].start();
        }

        for (int i = 0; i < consumeThread.length; i++) {
            consumeThread[i] = new Thread("消费者:" + i) {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String fruit = queue.take();
                            System.out.println(getName()+"消费了一个"+fruit+",当前队列大小:"+queue.size());
                            Thread.sleep((long)(Math.random()*300));
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            };
            consumeThread[i].start();
        }


    }

}
