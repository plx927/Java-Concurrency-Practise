package com.panlingxiao.syn.aid;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by panlingxiao on 2016/6/16.
 */
public class CyclicBarrierTest {

    static boolean isDone = false;

    public static void main(String[] args) throws InterruptedException {
        final float[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Solver solver = new Solver(matrix);
    }


    static boolean done() {
        return isDone;
    }

    public static class Solver {
        int N;
        float[][] matrix;
        final CyclicBarrier barrier;
        final List<Worker> workers = new ArrayList<Worker>();

        public Solver(float[][] matrix) throws InterruptedException {
            N = matrix.length;
            this.matrix = matrix;
            barrier = new CyclicBarrier(matrix.length, new Runnable() {
                public void run() {
                    isDone = true;
                    System.out.println("执行 barrier action");
                    float sum = 0;
                    for(Worker worker : workers){
                        sum += worker.result;
                    }
                    System.out.println(String.format("计算矩阵的结果为:%f",sum));
                }
            });

            List<Thread> threads = new ArrayList<Thread>(N);
            for (int i = 0; i < N; i++) {
                Worker worker = new Worker(matrix[i]);
                workers.add(worker);
                Thread thread = new Thread(worker);
                threads.add(thread);
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }
        }


        class Worker implements Runnable {
            float[] myRow;
            float result;
            public Worker(float[] myRow) {
                this.myRow = myRow;
            }

            public void run() {
                while (!done()) {
                    processRow(myRow);
                    try {
                    /*
                     * 等待其他线程全部调用完成await方法,到达barrier点之后才继续执行
                     * 如果当前线程不是最后一个执行await方法的线程,那么它将处于等待状态
                     * 如果当前线程是最后一个到达barrier的，非空的barrier action将又当前线程所执行
                     * 然后其他等待线程再继续执行(通过分析await源码可以看到)
                     * 因此barrier action的执行早于线程await方法的返回(happen before)
                     * await返回到达barrier的索引数
                     */
                        int n = barrier.await();
                        System.out.println(String.format("第%d个到达barrier", n));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void processRow(float[] myRow) {
                for (float num : myRow) {
                    result += num;
                }
            }
        }

    }


}
