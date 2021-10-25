package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/19
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【思路】
 *  a^b      就是无进位相加
 *  (a&b)<<1 得到的是进位信息
 *
 *  两者相加得到a+b
 *
 */
public class Problem_0371_SumOfTwoIntegers {

    /**
     * 递归版本
     */
    public static int getSum(int a, int b) {
        return b == 0 ? a ^ b : getSum(a ^ b, (a & b) << 1);
    }




    /**
     * 迭代版本
     */
    public static int getSum2(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }
}
