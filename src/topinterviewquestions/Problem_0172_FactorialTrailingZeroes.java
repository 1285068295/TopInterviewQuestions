package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/10
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】
 *  有多少个5因子就有多少个0
 *  每个5因子可以和一个2 结合成一个0
 *  如   1*2*3*4*5*6*7*8*9*10*11*12*13*14*15
 *  因为2是每隔2个数就能出现一个2因子  而5是每隔5个数才会出现一个5因子  所以 总的来说 2的因子要比5的因子多
 *  所以所有的5因子一定能找到一个2与之匹配得到10 一个0
 *
 * 结论1：只需要找因子2，5的个数，就能确定0的个数
 *
 * 结论2：2的个数比5多，所以只要找因子5的个数就行。
 *
 * 结论3：每间隔 5 个数有一个数可以被 5 整除, 然后在这些可被 5 整除的数中,
 *       每间隔 5 个数又有一个可以被 25 整除, 故要再除一次, ... 直到结果为 0,
 *       表示没有能继续被 5 整除的数了.
 *
 */
public class Problem_0172_FactorialTrailingZeroes {

    public static int trailingZeroes(int n) {

        // 5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 80 85 90 95 100




        int ans = 0;
        while (n != 0) {
            n /= 5;
            ans += n;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(trailingZeroes(10));
    }

}
