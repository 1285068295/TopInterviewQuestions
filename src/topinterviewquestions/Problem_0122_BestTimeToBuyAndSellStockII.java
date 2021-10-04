package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/4
 * @version: V1.0
 * @slogan:
 * @description :
 * 股票问题第二问  可以不限买卖次数
 * 【解题思路】
 *  找到数组中所有的股票上涨的区间段累加起来
 *  计算出每一个上涨的区间段的收集，累加起来就是总的最大收益
 *  只需要比较当前点紧邻的左侧数据是否比当前点值要小。
 *
 */
public class Problem_0122_BestTimeToBuyAndSellStockII {


    public int maxProfit(int[] prices) {


        int maxProfix = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i-1]){
                maxProfix += prices[i] - prices[i-1];
            }
        }

        return maxProfix;

    }
}
