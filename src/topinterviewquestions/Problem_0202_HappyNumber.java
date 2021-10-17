package topinterviewquestions;

import java.util.HashSet;

/**
 * @author ：Lisp
 * @date： 2021/10/10
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 用一个hashset 记录  初选重复值就停止计算
 *             一个很大的数经过计算后会逐渐收敛与一个越来越小的数
 *
 */
public class Problem_0202_HappyNumber {
    public static boolean isHappy(int n) {

        HashSet<Integer> set = new HashSet<>();


        while (n != 1) {
            if (set.contains(n)) {
                return false;
            }
            set.add(n);
            int t = 0;
            while (n != 0) {
                t += (n % 10) * (n % 10);
                n /= 10;
            }
            n = t;
        }
        return true;
    }


    /**
     * 使用数学公式法
     * n 最后一定是收敛于 1或者 4
     */
    public static boolean isHappy2(int n) {
        while (n != 1 && n != 4) {
            int sum = 0;
            while (n != 0) {
                sum += (n % 10) * (n % 10);
                n /= 10;
            }
            n = sum;
        }
        return n == 1;
    }


    public static void main(String[] args) {
        isHappy(19);
    }

}
