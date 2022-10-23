package com.jump.interview.questions.other;

import org.junit.Test;

public class Other_1 {
    /**
     * 1、有一个数组ROOT [n]，数组的元素类型为 class Node
     * <p>
     * public int index；//用来表示自己位于ROOT[n]中的位置序号
     * <p>
     * public Node next；//用来表示自己指向的下一级Node
     * <p>
     * next可能指向自身，也可能指向一个新的孤立的Node(并非ROOT数组中的根节点Node），也可能指向其他ROOT里的Node, 这样每个ROOT下面都可能形成一个单向的Node链表（可能会形成死循环链表）
     * <p>
     * 请写代码寻找不被其他Node链指向的Node链表（可以用 ROOTIn] = nu11设置为删除）
     * <p>
     * [Node1, Node2, Node3, Node4, Node5,. ... ]
     * <p>
     * Node1 Node2 Node3 Node4e-.-1
     * <p>
     * Node1->Node2<----------|
     * <p>
     * 其中Node1, Node3, Node5未被其他节点引用
     */
    @Test
    public void test1() {

    }

    /**
     * 2、假设有一个函数
     * <p>
     * bool testAndSet(int &value, int target, int before)（不需要自己实现）
     * <p>
     * 可以线程安全的比较value值是否为before，如果是的话就设置为target,
     * <p>
     * 同时返回true，否则不设置值并且返回false
     * <p>
     * 请基于此函数实现一个长度不超过100的线程安全的栈
     * <p>
     * （包含pop和push，不能使用synchonized/1ock/mutex/atomic/semaphore等关键字/类/系统API，可以使用volatile)
     */
    @Test
    public void test2() {
        MyLinkedStack<Integer> myStack = new MyLinkedStack<Integer>();
        for (int i = 0; i < 5; i++) {
            new Thread(() ->{

            });
        }
    }

    class MyArrStack<T> {
        private final int cap =100;
        private Object[] e = new Object[100] ;
        private volatile int top = -1;

        public boolean push(T t) {
            for (; ; ) {
                int index = top;
                if (index + 1 > 100) {
                    return false;
                }
                if (testAndSet(index, index + 1, top)) {
                    e[index +1] = t;
                    top=index+1;
                    return true;
                }
            }
        }

        public T pop() {
            for (; ; ) {
                int index = top;
                if (index == -1) {
                    return null;
                }
                if (testAndSet(index, index - 1, top)) {
                    top = index - 1;
                    return (T)e[index];
                }
            }
        }
    }


    class MyLinkedStack<T> {
        private volatile int size;
        private volatile Node<T> head = new Node<>(null);

        public void push(T t) {
            if (size > 10) {
                throw new RuntimeException("length limit");
            }
            //这行需要放到循环内
            Node<T> oldNode = head;
            Node<T> newNode = new Node<>(t);
            newNode.setNext(oldNode);
            while (testAndSet(oldNode.hashCode(), newNode.hashCode(), head.hashCode())) {
                head = newNode;
            }
            size++;
        }

        public T pop() {
            Node<T> newHead = head.getNext();
            Node<T> currentHead = head;
            while (testAndSet(currentHead.hashCode(), newHead.hashCode(), head.hashCode())) {
                head = newHead;
            }
            size--;
            return currentHead.getE();
        }

    }

    static class Node<E> {
        private final E e;
        private Node<E> next;

        public Node(E e) {
            this.e = e;
        }

        public E getE() {
            return e;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }



    public static boolean testAndSet(int value, int target, int before) {
        return true;
    }

    /**
     * 3、在一个抽奖活动中，用户抽奖必中，一共有6种奖品，每种奖品得数量和中奖概率分别为 [5,0.1%],[20,5.1%],[30,8.4%]，[40, 15.4%]，[100, 21.5%], [200, 49.5%]
     * <p>
     * 如果某一次抽奖抽中一类库存耗光的奖品后，则需要去除当前奖品后重新用剩余奖品得权重再算奖。
     * <p>
     * 请写一段代码来模拟这个抽奖，要求实际中奖概率可以基本符合设置要求
     */
    @Test
    public void test3() {

    }
}
