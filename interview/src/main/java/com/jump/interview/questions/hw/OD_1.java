package com.jump.interview.questions.hw;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OD_1 {

    /**
     * 字符串出现最多的字符打印出来
     * 输入"adddddsssggkkfs"
     * 输出"d"
     */
    @Test
    public void test_01() {
        String input= "adddddsssggkkfs";
        char[] chars = input.toCharArray();
        int maxCount = 0;
        char out = 0;
        Map<Character, Integer> map = new HashMap<Character, Integer>();

        for (char c : chars) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c,1);
            }
        }

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                out = entry.getKey();
            }
        }
        System.out.println(out);
    }

    /**
     * 连续出现次数最多的字符
     */
    @Test
    public void test_02() {
        String input= "adddddsssggkkfs";
        char[] chars = input.toCharArray();
        int max = 0;
        int macChar = 0;
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i > 0 && chars[i] == chars[i - 1]) {
                count++;
            }
        }
    }

    /**
     * 如{1,0,1,1,0,1,1,1,1,1,0}
     * 0表示空位
     * 1表示小汽车
     * 11表示大巴车
     * 111表示大卡车
     * 求，最少停车数量
     */
    @Test
    public void test_03() {
        int[] arr = new int[]{1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0};
        int t = 0;
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0 && t != 3) {
                t++;
            } else {
                t = 0;
                res++;
            }
        }
        System.out.println(res);
        Assert.assertEquals(4, res);
    }


    /**
     * 二维数组，0和1组成
     * 1表示当前行和列对应的服务器连通
     * 0表示不连通
     * 至少几个服务器发送消息，才能使整个集群收到消息
     *
     * todo 连通圆
     */
    @Test
    public void test_04() {
        int[][] source = new int[][]{
                {0,1,0,1,0,0,1,1},
                {0,0,0,1,0,1,1,0},
                {1,0,1,0,0,0,1,1}
        };


    }













}
