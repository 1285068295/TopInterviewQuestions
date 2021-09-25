package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/25
 * @version: V1.0
 * @slogan:
 * @description :
 * 【高中配列组合问题】
 * 一共要走 (m-1) + (n-1) 步  其中(m-1)向下走  (n-1)向右走    方案数C(m-1, m+n-2)  或者 C(n-1, m+n-2)
 *
 * 高中知识知 C(m-1, m+n-2)  = (m+n-2)! / [(m-1)! * (n-1)!]
 *
 * 难点在于 如果尽可能的保证计算过程中尽可能的不发生越界的情况！！！
 *
 *
 * C(4, 9) = 9*8*7*6*5*4*3*2*1 / (4*3*2*1  *  5*4*3*2*1)
 *         = 9*8*7*6 / 4*3*2*1
 *         = 6*7*8*9 / 1*2*3*4   先求6    和1的最大公约数1 把6   和1 同时除最大公约数1
 *         = 6*7*8*9 / 2*3*4     先求6*7  和2的最大公约数2 把6*7 和2 同时除最大公约数2  3*7
 *         = 3*7*8*9 / 3*4       先求3*7*8和3的最大公约数3 把3*7*8和3 同时除最大公约数3 7*8
 *         =   7*8*9 / 4         先求7*8*9和4的最大公约数4 把7*8*9和4 同时除最大公约数4 7*2*4
 *         没计算一步都要先约去最大公约数
 *
 *
 */
public class Problem_0062_UniquePaths {


    /**
     * 1 <= m, n <= 100
     */
    public static int uniquePaths(int m, int n) {
        int part = m > n ? n - 1 : m - 1;
        int all = m + n - 2;

        long up = 1;
        long down = 1;
        // 9*8*7 = 9*(9-1)*(9-2)
        // 3*2*1 = 3 * 2  * 1
        for (long i = 1; i <= part; i++) {

            long t = gcd(i * down, (all - part + i) * up);
            // 踩坑点
            //  up *= (all - part + i) /t  up=15 all-part+i = 7  t= 3  会算出来up = 30 实际应为35
            up = up * (all - part + i) / t;
            down = down * i / t;
        }
        return (int) (up/ down);
    }


    /**
     * 辗转相处法求最大公约数  第一次调用此方法时要保证n m 都不为0
     *
     * m和n 的最大公约数  =  m%n 和  n的最大公约数
     *
     * 【代码直接背过！！！】
     *
     */
    public static long gcd(long m, long n) {
        return n == 0 ? m : gcd(n, m % n);
    }


    public static void main(String[] args) {

        System.out.println(gcd(14, 21));
        System.out.println(uniquePaths(5, 5));

    }
}
