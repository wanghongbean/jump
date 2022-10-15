package com.jump.interview.questions.ks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编程实现一个简单的生产者消费者模型，一个生产者从给定的文本文件中按行读取数据，
 * 3个消费者均匀的消费产生的数据，统计数据长度，并打印出来，直到所有数据全部消费完毕，
 * 最后生产者和消费者线程结束退出。
 */
public class Ks_1 {
    public static final String FILE_PATH = "/ks_01.txt";
    public static volatile int NEXT = 1;


    public static String POISON_PILL = "poisonPill";

    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();

        AtomicInteger res = new AtomicInteger();
        AtomicInteger stop = new AtomicInteger(1);
        Thread producer = new Thread(() -> {
            if (Thread.interrupted()) {
                return;
            }
            readLine(queue);
        }, "producer");

        Thread consumer1 = new Thread(getRunnable(queue, lock, condition1, condition2, stop, 1, 2, res), "consumer1");
        Thread consumer2 = new Thread(getRunnable(queue, lock, condition2, condition3, stop, 2, 3, res), "consumer2");
        Thread consumer3 = new Thread(getRunnable(queue, lock, condition3, condition1, stop, 3, 1, res), "consumer3");
        producer.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        while (stop.get() != 3) {

        }
        System.out.println("total size : " + res);
        producer.interrupt();

        consumer1.interrupt();
        consumer2.interrupt();
        consumer3.interrupt();

        System.exit(0);
    }

    private static void readLine(LinkedBlockingQueue<String> queue) {
        //按行读取文件
        InputStream ras = Ks_1.class.getResourceAsStream(FILE_PATH);
        InputStreamReader inputStreamReader = new InputStreamReader(ras);
        BufferedReader bir = new BufferedReader(inputStreamReader);
        String line;
        try {
            while ((line = bir.readLine()) != null) {
                queue.put(line);
//                System.out.println("== " + line);
            }
            queue.put(POISON_PILL);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Runnable getRunnable(LinkedBlockingQueue<String> queue, ReentrantLock lock, Condition condition, Condition cond, AtomicInteger stop, int target, int next, AtomicInteger res) {
        return () -> {
            boolean running = true;
            while (running) {
                lock.lock();
                try {
                    while (NEXT != target) {
                        condition.await();
                    }
                    String take = queue.take();
                    if (!POISON_PILL.equals(take)) {
                        System.out.println(Thread.currentThread().getName() + " : " + take);
                        res.getAndAdd(take.length());
                    } else {
                        running = false;
                        queue.put(POISON_PILL);
                        stop.getAndIncrement();
                    }
                    if (Thread.interrupted()) {
                        break;
                    }
                    NEXT = next;
                    cond.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };
    }

}
