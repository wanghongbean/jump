package com.jump.interview.questions.hw;

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
}
