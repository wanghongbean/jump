package com.jump.interview.questions.qzwl;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 题目 https://gitee.com/beanCloud/note-image/raw/master/noteImg/202210211227243.png
 */
public class Qingziweilai {

    @Test
    public void test_02() {
        MyStack<Integer> stack = new MyStack<>(100);
        int max = 20;
        Thread[] threads = new Thread[max];
        for (int i = 0; i < max; i++) {
            int temp = i;
            //入栈1、2、3
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    stack.push(temp + 1);
                }
            });
            thread.start();
            threads[temp] = thread;
        }
        //等待所有线程完成。
        for (int i = 0; i < max; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }

        for (int i = 0; i < max; i++) {
            //出栈3、2、1
            System.out.println(stack.pop());
        }
    }

    static class MyStack<T> {
        private int cap;
        private Object[] e;
        private AtomicInteger top = new AtomicInteger(-1);

        public MyStack(int cap) {
            e = new Object[cap];
            this.cap = cap;
        }

        public boolean push(T o) {
            while (true) {
                int cur = top.get();
                if (cur + 1 > cap) {
                    return false;
                } else {
                    if (top.compareAndSet(cur, cur + 1)) {
                        e[cur + 1] = o;
                        return true;
                    }
                }
            }
        }

        public Object pop() {
            while (true) {
                int cur = top.get();
                if (cur == -1) {
                    return null;
                } else {
                    if (top.compareAndSet(cur, cur - 1)) {
                        return (T) e[cur];
                    }
                }
            }
        }

    }

    /**
     * 3、在一个抽奖活动中，用户抽奖必中，一共有6种奖品，
     * 每种奖品得数量和中奖概率分别为 [5,0.1%]，[20, 5.1%], [30,8.4%],[40, 15.4%], [100, 21.5%], [200, 49.5%]
     * 如果某一次抽奖抽中一类库存耗光的奖品后，则需要去除当前奖品后重新用剩余奖品得权重再算奖，
     * 请写一段代码来模拟这个抽奖，要求实际中奖概率可以基本符合设置要求
     */
    @Test
    public void test_choujing() {
        Random random = new Random();
        int[] s = new int[]{5, 20, 30, 40, 100, 200};
        double[] sgld = new double[]{0.001, 0.051, 0.084, 0.154, 0.215, 0.495};
        int[] sgl = new int[]{1, 51, 84, 154, 215, 495};

        int[] arr = new int[6];
        for (int i = 0; i < 400; i++) {
            int res = random(s, sgld);
            if (res == -1) {
                break;
            }
            arr[res] = arr[res] + 1;
            System.out.println("第 " + i + " 次，抽出 " + res);
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println("奖品 " + i + " 中奖 " + arr[i] + " 次");
        }

    }

    private static int random(int[] s, double[] sgl) {
        double v = Math.random();
        double max = 0d;
        int maxIdx = -1;
        for (int i = 0; i < sgl.length; i++) {
            if (sgl[i] > max) {
                max = sgl[i];
                maxIdx = i;
            }
        }
        int total = 0;
        for (int i = 0; i < s.length; i++) {
            total += s[i];
        }
        if (maxIdx == -1 || max == 0d || total == 0) {
            return -1;
        }

        for (int i = 0; i < sgl.length; i++) {
            if (v <= sgl[i]) {
                if (s[i] > 0) {
                    s[i] -= 1;
                    return i;
                } else {
                    double[] newGl = reCompute(sgl, i);
                    return random(s, newGl);
                }
            }
        }
        if (s[maxIdx] > 0) {
            s[maxIdx] -= 1;
            return maxIdx;
        } else {
            double[] newGl = reCompute(sgl, maxIdx);
            return random(s, newGl);
        }
    }

    public static double[] reCompute(double[] s, int t) {
        double v = s[t];
        BigDecimal total = BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(v));
        s[t] = -1;
        BigDecimal vv = new BigDecimal(v);
        for (int i = 0; i < s.length; i++) {
            if (i == t) {
                continue;
            }
            s[i] = (BigDecimal.valueOf(s[i]).add(BigDecimal.valueOf(s[i]).divide(total, 3, RoundingMode.HALF_UP).multiply(vv))).doubleValue();
        }
        return s;
    }

    /**
     * 4、一个数组，里面全是数字，从左致右的特征是：从小到大，然后从大到小，数组中可能会出现连续重复的数字。
     * 请写一段代码<最快>找出数组中最大的数值（最大值可能为1到多个），
     * 输出最大数字和个数
     * 比如数组[1,3,3,3.4,5.6,7,8,8,8,8,8,9,10, 10, 11,11, 12,23,33,33, 18, Z,2,2,2,2,2,1,1,1]
     * 二分查找
     */
    @Test
    public void test_04() {
        int[] source = new int[]{1, 3, 3, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 9, 10, 10, 11, 11, 12, 23, 33, 33, 18, 2, 2, 2, 2, 2, 2, 1, 1, 1};
        int max = -1;
        int n = 0;
        //暴力循环
        for (int i = 0; i < source.length; i++) {
            if (source[i] > max) {
                max = source[i];
                n=1;
            } else if (source[i] == max) {
                n++;
            }

            if (i != 0 && i!=source.length-1
                    && source[i - 1] <= source[i]
                    && source[i] > source[i + 1]) {
                break;
            }

        }
        System.out.println(max +" "+ n);

        //二分查找
        int l = 0;
        int r = source.length - 1;

        int maxIdx = -1;
        int maxN = 1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (source[mid - 1] <= source[mid] && source[mid] > source[mid + 1]) {
                maxIdx = mid;
                break;
            } else if (source[mid - 1] <= source[mid] && source[mid] <= source[mid + 1]) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        for (int i = maxIdx-1; i >=0; i--) {
            if (source[maxIdx] == source[i]) {
                maxN++;
            }
        }
        System.out.println(source[maxIdx] + " " + maxN);

    }


    /**
     * 5、数据库中有一张表category，存储了商品分类数据，
     * 字段包含：id, parent_id，name，其中parent_id表示父级分类得id parent_id都会小于id，
     * 读取数据库后得到一个category[N]数组（可以用category[O][“id"]这样来获取第1行得分类id数据）
     * 请设计一个类Category用来表达一个分类，并且通过category[N]构建一个Category树，
     * 来表达整个商品分类得树型结构关系，所有分类得根节点可以自行构造一个id=0,parent_id-0得Category来表达。
     *
     * 多叉树，广度优先BFS ?
     * https://blog.51cto.com/u_14299052/5220525
     */
    @Test
    public void test_05() {
        int n = 20;
        Category[] init = new Category[20];

    }

    public Category get(Category root,int row, int col) {
        if (root.getId() != 0) {
            return null;
        }
        Queue<Category> queue = new LinkedList<>();
//        int[] visited = new int[];
        int[] prev = new int[];
        while (queue.size() != 0) {

        }

    }


    class Category {
        private int id;
        private int parentId;
        private List<Category> children = new ArrayList<>();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Category> getChildren() {
            return children;
        }

        public void setChildren(List<Category> children) {
            this.children = children;
        }
    }

    @Test
    public void test() {
        int[] ar = new int[]{9,8,7};
        for (int i = 0; i < ar.length; ++i) {
            System.out.println(ar[i]);
        }
        System.out.println("========");
        for (int i = 0; i < ar.length; i++) {
            System.out.println(ar[i]);
        }
    }

}
