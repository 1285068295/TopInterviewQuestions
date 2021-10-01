package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/27
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 斐波那契数列
 *
 * 类似斐波那契数列的递归优化
 * 如果某个递归，除了初始项之外，具有如下的形式
 * F(N) = C1 * F(N) + C2 * F(N-1) + … + Ck * F(N-k) ( C1…Ck 和k都是常数)
 * 并且这个递归的表达式是严格的、不随条件转移的
 * 那么都存在类似斐波那契数列的优化，时间复杂度都能优化成O(logN)
 * 时间复杂度为 O(logN) 一定时最优解  比O(N) 强太多了。
 *
 * 对于斐波那契数列  有线性代数知识可以知道
 * | F(3) , F(2) | = | F(2), F(1) |  *   某个二阶矩阵
 * | F(4) , F(3) | = | F(3), F(2) |  *   某个二阶矩阵
 * | F(5) , F(4) | = | F(4), F(3) |  *   某个二阶矩阵
 * ...
 *
 * 由上面的式子可以得到：
 * 对于二阶问题   | F(N) , F(N-1) | = | F(2), F(1) |  *   某个二阶矩阵的N-2次方
 * 对于三阶问题   | F(N) , F(N-1), F(N-2) | = |F(3), F(2), F(1) |  *  某个三阶矩阵的N-3次方
 * 矩阵的解法  带入数列的前几项  一定可以解出二阶矩阵
 *
 *
 *
 * 对于斐波那契数列  最终  |F(N), F(N-1)| =  |F(2), F(1)|  *   { { 1, 1 }, { 1, 0 } } ^(n-2)
 *
 * 本题求解  设二阶矩阵为 { { a, b }, { c, d } }  数列前几项为   1 2 3 5 8
 *
 *  |F(3), F(2)| =  |F(2), F(1)|  *   { { a, b }, { c, d } } ^(3-2)
 *  |3, 2| =  |2, 1|  *   { { a, b }, { c, d } }   ->3=2a+c  2=2b+d
 *  |5, 3| =  |3, 2|  *   { { a, b }, { c, d } }   ->5=3a+2c  3=3b+2d    a=1  b=1 c=1 d=0
 *
 *
 *
 *
 *
 */
public class Problem_0070_ClimbingStairs {

    /**
     * 动态规划版 时间复杂度O(N)
     *
     * 垃圾东东！！！
     */
    public static int climbStairs(int n) {

        if (n== 1){return 1;}
        if (n==2){return 2;}

        int f1 = 1, f2 = 2;
        int fn = 0;
        for (int i = 3; i <= n; i++) {
            fn = f1 +f2;
            f1 = f2;
            f2 = fn;
        }
        return fn;
    }






    /**
     * 利用线性方程求解
     */
    public static int climbStairs2(int n) {
        if (n== 1){return 1;}
        if (n==2){return 2;}

        // f2 = 2  f1 = 1
        // [ 1 ,1 ]
        // [ 1, 0 ]
        int[][] base = { { 1, 1 }, { 1, 0 } };
        int[][] res = matrixPower(base, n - 2);

        // 【需要注意！！！】难点在于求出k阶矩阵
        // |F(N), F(N-1)| =  |F(2), F(1)|  *   { { 1, 1 }, { 1, 0 } } ^(n-2)
        // 矩阵相乘得到对应位置的数据就是答案！！！
        return 2*res[0][0] + res[1][0];
    }

    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // res = 矩阵中的1
        int[][] tmp = m;// 矩阵1次方
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = muliMatrix(res, tmp);
            }
            tmp = muliMatrix(tmp, tmp);
        }
        return res;
    }

    public static int[][] muliMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }




    public static void main(String[] args) {
        // 1 2 3 5 8
        System.out.println(climbStairs(3));
        System.out.println(climbStairs(4));
        System.out.println(climbStairs(5));
    }
}
