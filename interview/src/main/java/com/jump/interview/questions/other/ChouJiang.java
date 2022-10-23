package com.jump.interview.questions.other;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ChouJiang {
    private List<JiangPin> list = new ArrayList(6);

    private Map<Integer,Integer> gl = new HashMap(6);
    private List<Integer> gli = new ArrayList<>(6);
    private Random random = new Random();

    {
        JiangPin jp1 = new JiangPin(1,5);
        list.add(jp1);
        JiangPin jp2 = new JiangPin(2,20);
        list.add(jp2);
        JiangPin jp3 = new JiangPin(3,30);
        list.add(jp3);
        JiangPin jp4 = new JiangPin(4,40);
        list.add(jp4);
        JiangPin jp5 = new JiangPin(5,100);
        list.add(jp5);
        JiangPin jp6 = new JiangPin(6,200);
        list.add(jp6);
        computeI();
    }

    public void compute() {
        int total = (int)list.stream().map(JiangPin::getCount).count();
        for (JiangPin jp : list) {
            gl.put((jp.getCount() / total) * 1000, jp.getId());
        }
    }

    public void computeI() {
        int total = (int)list.stream().map(JiangPin::getCount).filter(count -> count >0).count();
        list.sort(Comparator.comparingInt(JiangPin::getCount));
        for (JiangPin jp : list) {
            BigDecimal t = new BigDecimal(total);
            BigDecimal divide = new BigDecimal(jp.getCount()).divide(t,2, RoundingMode.HALF_UP);
            int v = divide.multiply(new BigDecimal(10000)).intValue();
            gli.add(v);
        }
    }


    public int next(){
        if (list.isEmpty()) {
            return -1;
        }
        int random = this.random.nextInt(10000);
        for (int i = 0; i < gli.size(); i++) {
            if (random <= gli.get(i)) {
                if (i >= list.size()) {
                    return -1;
                }
                JiangPin next = list.get(i);
                next.remove();
                if (next.getCount() == 0) {
                    list.remove(i);
                    computeI();
                    next();
                }
                return list.get(i).getId();
            }
        }
        return -1;
    }

    static class JiangPin{
        private int id;
        private int count;

        public JiangPin(int id, int count){
            this.id=id;
            this.count=count;
        }

        public int getId() {
            return id;
        }

        public int getCount(){
            return this.count;
        }

        public void remove(){
            this.count--;
        }
    }

    public static void main(String[] args) {
        ChouJiang chouJiang = new ChouJiang();
        for (int i = 0; i < 500; i++) {
            int next = chouJiang.next();
            System.out.println("第 " +i+" 次抽奖，抽出："+next);
        }
    }

}
