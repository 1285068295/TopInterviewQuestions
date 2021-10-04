package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/4
 * @version: V1.0
 * @slogan:
 * @description :股票问题第一问
 * 【思路】
 * 尝试所有的i位置  必须在i位置卖出股票的情况。
 * max{ 用i位置的股票价格  -  0~i位置上的最低的股票价格 }
 */
public class Problem_0121_BestTimeToBuyAndSellStock {



    public static int maxProfit(int[] prices) {


        // 0~i位置上的最低价格
        int min = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            min =Math.min(min, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i] - min);
        }


        return maxProfit;



    }

    public static void main(String[] args) {
        int[] arr ={7,1,5,3,6,4};

        System.out.println(maxProfit(arr));
    }
}
