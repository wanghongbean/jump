package com.jump.interview.questions.ruiqi;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.*;

public class RuiQi {


    public static Map<String, Map> list2map(String[] strArr, int i) {
        if (strArr.length - 1 == i) {
            HashMap<String, Map> map = new HashMap<>();
            map.put(strArr[i], new HashMap<String, Map>());
            return map;
        } else {
            Map<String, Map> map = new HashMap<>();
            String s = strArr[i];
            map.put(s, list2map(strArr, ++i));
            return map;
        }
    }


    public static void mergeMap(Map<String, Map> root, Map<String, Map> map) {
        for (String key : map.keySet()) {
            if (root.containsKey(key)) {
                mergeMap(root.get(key), map.get(key));
            } else {
                root.put(key, map.get(key));
            }
        }
    }

    public static Map<String, Map> patList2Map(List<String> list) {
        Map<String, Map> root = new HashMap<>();
        List<Map> allMap = new ArrayList<>();
        for (String str : list) {
            String[] split = str.substring(1).split("/");
            Map<String, Map> mapLine = list2map(split, 0);
            allMap.add(mapLine);
        }
        System.out.println(JSON.toJSONString(allMap));

        for (int i = 0; i < allMap.size(); i++) {
            mergeMap(root, allMap.get(i));
        }


        return root;

    }

    @Test
    public void test() {
        List<String> pathList = Arrays.asList(
                "/etc/hosts",
                "/etc/kubernetes/ssL/certs",
                "/root");
        Map<String, Map> result = patList2Map(pathList);
        System.out.println(JSON.toJSONString(result));
    }

}
