package com.panlingxiao.concurrency.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created by panlingxiao on 2016/8/4.
 * AtomicReferenceFieldUpdater学习
 * 当我们的属性已经在之前定义好了，但是又希望对其实现原子更新操作,那么可以使用AtomicReferenceFieldUpdater来完成。
 */
public class AtomicReferenceFieldUpdaterExample {


    public static void main(String[] args) {

        Node parent = new Node();
        //获取next节点，最初的next节点为null
        Node nextNode = parent.getNext();
        System.out.println("Original Next : "+nextNode);

        Node node1 = new Node();
        //原子更新对象的属性
        System.out.println("Update Success : "+parent.setNext(node1));
        //获取next与更新值比较，判断是否相等
        System.out.println("Compare Result : "+(parent.getNext() == node1));

        Node node2 = new Node();
        System.out.println("Update Success : "+parent.setNext(node2));

    }

    public static class Node{
        //属性必须是volatile
        private volatile Node next = null;
        private AtomicReferenceFieldUpdater<Node,Node> nextUpdater = AtomicReferenceFieldUpdater.newUpdater(Node.class,Node.class,"next");

        public boolean setNext(Node node){
            try {
                boolean ret  = nextUpdater.compareAndSet(this,next,node);
                return ret;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        public Node getNext(){
            return nextUpdater.get(this);
        }
    }
}
