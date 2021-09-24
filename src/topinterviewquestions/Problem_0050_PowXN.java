package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/25
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * x的N次方的实现 时间复杂度可以做到 logN 级别
 *
 * 【实现思路】
 *  如求  a^75   75用二进制表示   1+2+8+64 =   1001011  = a  * a^2 * a^8 * a^64
 *   (1*a^0 = 1) *  1*a^1 *  1*a^2 * 0*a^3 *  0*a^4 *  0*a^5*  1*a^6
 *
 * 【提示】
 *       -100.0 < x < 100.0
 *       -2^31 <= n <= 2^31 - 1
 *       -104 <= xn <= 104
 *
 */
public class Problem_0050_PowXN {

    /**
     * 简易版实现方式 最粗糙的实现
     * @param a
     * @param N
     * @return
     */
    public static double myPow1(double a, int N){
        // 100100
        // 0*a^0 *  0*a^1 *  1*a^2 * 0*a^3 *  0*a^4 *  1*a^5

        double res = 1;
        while (N > 0) {
            if ((N & 1) == 1) {
                res *= a;
            }
            a *= a;
            N = N >> 1;
        }
        return res;
    }




    /**
     * 优化版本
     *
     * 考虑N为负数的情况
     */
    public static double myPow2(double a, int N) {
        if (N == 0) {
            return 1;
        }

        double tmp =a;
        // 需要考虑系统最小比系统最大 范围要大一个  先做加1处理
        // - Integer.MIN_VALUE = Integer.MIN_VALUE  不能直接转
        int M = N < 0 ? -(1 + N) : N - 1;
        double res = 1;
        while (M > 0) {
            if ((M & 1) == 1) {
                res *= a;
            }
            a *= a;
            M = M >> 1;
        }

        res *= tmp;

        return N < 0 ? 1 / res : res;
    }





    /**
     * teacher 给的错误做法  测试用力见main函数
     * @param x
     * @param n
     * @return
     */
    public static double myPow11(double x, int n) {
        if (n == 0) {
            return 1D;
        }
        if (n == Integer.MIN_VALUE) {
            return (x == 1D || x == -1D) ? 1D : 0;
        }
        int pow = Math.abs(n);
        double t = x;
        double ans = 1D;
        while (pow != 0) {
            if ((pow & 1) != 0) {
                ans *= t;
            }
            pow >>= 1;
            t = t * t;
        }
        return n < 0 ? (1D / ans) : ans;
    }



    /**
     * teacher 给的第二版的代码
     * @param x
     * @param n
     * @return
     */
    public static double myPow22(double x, int n) {
        if (n == 0) {
            return 1D;
        }
        int pow = Math.abs(n == Integer.MIN_VALUE ? n + 1 : n);
        double t = x;
        double ans = 1D;
        while (pow != 0) {
            if ((pow & 1) != 0) {
                ans *= t;
            }
            pow >>= 1;
            t = t * t;
        }
        if (n == Integer.MIN_VALUE) {
            ans *= x;
        }
        return n < 0 ? (1D / ans) : ans;
    }

    public static void main(String[] args) {
        System.out.println("world shut up!");
        int a = Integer.MIN_VALUE;
        int b = -a;
        System.out.println(b);

        System.out.println("==============");

        double test = 1.00000001D;
        int N = Integer.MIN_VALUE;
        System.out.println(test == 1D);
        System.out.println(test + "的" + N + "次方，结果：");
        System.out.println(Math.pow(test, (double) N));
        System.out.println(myPow11(test, N));
        System.out.println(myPow22(test, N));
        // 我的解答
        System.out.println(myPow2(test, N));
    }

}
