package com.jump.interview.questions.weee;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.*;

public class TestWeee {


    public static void main(String[] args) {
        int[] in = new int[]{56, 13, 26, 35, 32, 42, 72};
        int[] sort = sort(in);
        System.out.println(Arrays.toString(sort));//[13， 26， 35， 32， 42， 56， 72]
        int[] in2 = new int[]{18, 65, 77, 79, 5, 41, 57, 72};
        int[] sort2 = sort(in2);
        System.out.println(Arrays.toString(sort2));//[5，18，57，41，72，65，77，79]

    }

    public static int[] sort(int[] s) {
        int[] r = new int[s.length];
        //初始化货架，并分组
        List<int[][]> initList = init();
        //把入参按照所属货架组 分组
        Set<Integer>[] pst = formatParam(s);

        //按照顺序获取货物
        int ri = 0;
        boolean f = true;
        for (int i = 0; i < pst.length; i++) {
            if (pst[i] == null || pst[i].isEmpty()) {
                continue;
            }
            int[][] ints = initList.get(i);
            if (f) {
                for (int k = 0; k < ints[0].length; k++) {
                    if (pst[i].contains(ints[0][k])) {
                        r[ri] = ints[0][k];
                        pst[i].remove(ints[0][k]);
                        ri++;
                    }
                    if (pst[i].contains(ints[1][k])) {
                        r[ri] = ints[1][k];
                        pst[i].remove(ints[1][k]);
                        ri++;
                    }
                }
                f = false;
            } else {
                for (int k = ints[0].length - 1; k > 0; k--) {
                    if (pst[i].contains(ints[0][k])) {
                        r[ri] = ints[0][k];
                        pst[i].remove(ints[0][k]);
                        ri++;
                    }
                    if (pst[i].contains(ints[1][k])) {
                        r[ri] = ints[1][k];
                        pst[i].remove(ints[1][k]);
                        ri++;
                    }
                }
                f = true;
            }
        }
        return r;
    }

    private static Set<Integer>[] formatParam(int[] s) {
        //5组货架
        Set<Integer>[] ss = new Set[5];
        for (int i = 0; i < s.length; i++) {
            int z = s[i] / 20;
            if (ss[z] == null) {
                HashSet<Integer> st = new HashSet<>();
                st.add(s[i]);
                ss[z]=st;
            } else {
                ss[z].add(s[i]);
            }
        }
        return ss;
    }

//    private static List<Set<Integer>> formatParam(int[] s) {
//        //5组货架
//        List<Set<Integer>> list = new ArrayList<>(5);
//        HashSet<Integer>[] arr = new HashSet[5];
//        for (int i = 0; i < s.length; i++) {
//            int z = s[i] / 20;
//            if () {
//                HashSet<Integer> st = new HashSet<>();
//                st.add(s[i]);
//                list.add(z, st);
//            } else {
//                list.get(z).add(s[i]);
//            }
//        }
//        return list;
//    }

    /**
     * 0  1  2  3  4  5  6  7  8  9
     * >>---------------------------
     * 10 11 12 13 14 15 16 17 18 19
     * 20 21 22 23 24 25 26 27 28 29
     * -----------------------------
     * 30 31 32 33 34 35 36 37 38 39
     * 40 41 42 43 44 45 46 47 48 49
     * -----------------------------
     * 50 51 52 53 54 55 56 57 58 59
     * 60 61 62 63 64 65 66 67 68 69
     * -----------------------------
     * 70 71 72 73 74 75 76 77 78 79
     * 80 81 82 83 84 85 86 87 88 89
     * -----------------------------
     * 90 91 92 93 94 95 96 97 98 99
     *
     * @return
     */
    public static List<int[][]> init() {
        int[] base = new int[100];
        for (int i = 0; i < 100; i++) {
            base[i] = i;
        }


        List<int[][]> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int[][] temp = new int[2][10];
            for (int j = 0; j < 10; j++) {
                temp[0][j] = base[(i - 1) * 20 + j];
                temp[1][j] = base[(i - 1) * 20 + 10 + j];
            }
            list.add(temp);
        }
        return list;
    }

    @Test
    public void test() {
//        System.out.println(JSON.toJSONString(init()));
        for (int[][] ints : init()) {
            for (int[] anInt : ints) {
                System.out.println(JSON.toJSONString(anInt));
//                System.out.println("---------------------");
            }
            System.out.println("==========================");
        }
    }

    @Test
    public void test_1() {
        //[13， 26， 35， 32， 42， 56， 72]
        int[] in = new int[]{56, 13, 26, 35, 32, 42, 72};
        int[] sort = sort(in);
        System.out.println(JSON.toJSONString(sort));
//        Set<Integer>[] ss = formatParam(in);
//        System.out.println(JSON.toJSONString(ss));
//        System.out.println(56 / 20);
//        System.out.println(36 / 20);
//        System.out.println(18 / 20);
        System.out.println(0%2);
        System.out.println(1%2);
    }
}
