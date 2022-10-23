package com.jump.interview.algorithm.dynamic;

import org.junit.Test;

/**
 * 动态规划
 */
public class DynamicPrograming {

    /**
     * 数字三角问题
     * 第i层有i个元素
     * 第 i 层由 i 个数字组成，目标从第 1 层开始，每次只能向下走到相邻的两个节点，求走到最后一层路径上面数字的最大和值是多少
     */
    @Test
    public void test_maxValue() {
        int[][] arr = new int[][]{
                {8},
                {8,2},
                {1,4,6},
                {10,2,3,6}
        };
        System.out.println(maxValue(arr));
    }

    public int maxValue(int[][] arr) {
        int max = 0;
        int[][] dp = new int[arr.length][arr.length];
        dp[0][0] = arr[0][0];
        for (int i = 1; i < arr.length; i++) {
            System.out.println("i - "+i );
            dp[i][0] = dp[i - 1][0] + arr[i][0];
            max = Math.max(max, dp[i][0]);
            for (int j = 1; j <=i; j++) {
                dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i - 1][j]) + arr[i][j];
                max = Math.max(max, dp[i][j]);
                System.out.println("j = "+j+" max="+max);
            }
        }
        return max;
    }

    /**
     *
     * 问题：给你一个可放总重量为 W 的背包和 N 个物品，对每个物品，
     * 有重量 w 和价值 v 两个属性，那么第 i 个物品的重量为 w[i]，价值为 v[i]。现在让你用这个背包装物品，问最多能装的价值是多少？
     * 示例：
     * 输入：W = 5, N = 3
     * w = [3, 2, 1], v = [5, 2, 3]
     * 输出：8
     * 解释：选择 i=0 和 i=2 这两件物品装进背包。它们的总重量 4 小于 W，同时可以获得最大价值 8。
     * ----------
     * 0/1 背包状态定义：dp[i][j] 代表使用前 i 个物品，背包最大载重为 j 的情况下的最大价值总和。
     * 状态转移方程：
     *  - dp[i][j] = max(dp[i-1][j],dp[i-1][j-w[i]]+vi)
     *  - dp[i+1][j] = max(dp[i][j],dp[i+1][j-w[i]]+vi)
     */
    @Test
    public void test_package_1() {
        int[] w = new int[]{3, 2, 1};
        int[] v = new int[]{5, 2, 3};
        int max = packageMax1(w, v, 5, 3);
        System.out.println(max);
    }


    /**
     *
     * @param w 物品重量数组
     * @param v 物品价值数组
     * @param packageW 背包可装入的总重量
     * @param n 物品个数
     * @return 最大价值
     */
    public int packageMax1(int[] w,int[] v,int packageW,int n) {
        int[][] dp = new int[n+1][packageW+1];
        //初始化dp[0] ? 不放物品时，不同剩余重量 时的 value=0
//        for (int i = 0; i < packageW; i++) {
//            dp[0][i] = 0;
//        }

        int max = 0;
        for (int i = 0; i <n; i++) {
            for (int j = 0; j <=packageW; j++) {
                if (j < w[i]) {
                    continue;
                }
                dp[i+1][j] = Math.max(dp[i][j], dp[i][j - w[i]] + v[i]);
                max = Math.max(max, dp[i+1][j]);
            }
        }
        return max;
    }


    /**
     * 完全背包问题
     * 问题：给你一个可放总重量为 W 的背包和 N 个物品，对每个物品，有重量 w 和价值 v 两个属性，那么第 i 个物品的重量为 w[i]，价值为 v[i]。
     * 现在让你用这个背包装物品，每种物品都可以选择任意多个，问这个背包最多能装的价值是多少？
     * 示例：
     * 输入：W = 5, N = 3
     * w = [3, 2, 1], v = [5, 2, 3]
     * 输出：15
     * 解释：当 i = 2 时，选取 5 次，总价值为 5 * 3 = 15。
     * -------------
     * 状态参数：放入背包中的物品数量n，背包还剩下的重量w,此时的价值
     * 状态转移方程：dp[n][w] =
     */
    @Test
    public void test_package_2() {
        int[] w = new int[]{3, 2, 1};
        int[] v = new int[]{5, 2, 3};
        int max = packageMax2(w, v, 5, 3);
        System.out.println(max);
    }

    public int packageMax2(int[] w,int[] v,int pw,int num) {
        int[][] dp = new int[num+1][pw+1];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j <= pw; j++) {
                for (int k = 0; k <= j / w[i]; k++) {
                    if (j < k*w[i]) {
                        continue;
                    }
                    dp[i + 1][j] = Math.max(dp[i][j], dp[i][j - k * w[i]] + k * v[i]);
                }
            }
        }
        return dp[num][pw];
    }
}
