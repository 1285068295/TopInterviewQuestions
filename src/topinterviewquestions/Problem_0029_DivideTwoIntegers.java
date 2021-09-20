package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/19
 * @version: V1.0
 * @slogan:
 * @description : 只是用位运算来实现  加减乘除
 * 加法：
 *    a+b = (a^b) + (a&b << 1)  即  a异或b 是无进位相加运算  加上  a与b 右移一位得到进位信息
 *    又因为我们不能用加法运算  所以要一直递归  直到进位信息为0位置
 *
 * 减法：
 *     a-b = a + (-b)
 * 乘法：
 *             0 1 1 0 1 0
 *           x 0 0 1 1 0 1
 *          ---------------
 *             0 1 1 0 1 0
 *           0 0 0 0 0 0
 *         0 1 1 0 1 0
 *   +   0 1 1 0 1 0
 *   -----------------------
 *   b&1得到最末尾的数字是否为1
 *   循环相加  a向左移动1  b向右移动1位  如果b的最末尾位1  a加上a移动结果  否则不相加
 *
 *
 * 除法：
 *      整体的思路位  统一转为正数来求解  需要注意的是 系统最小  Integer.MIN_VALUE 比系统最大 Integer.MAX_VALUE大一  不能直接转成正数
 *      分情况讨论
 *      1 除数是系统最小      被除数是系统最小  返回-1
 *      2 除数不是位系统最小  被除数为系统最小  返回0
 *      3 除数是系统最小       被除数不是系统最小    需要补偿运算
 *        假设系统最小位 -10  自动最大为 9  要计算  -10/3
 *        [(-9) + (-1)] / 3  =  (-9)/3  + (-1)/ 3   分开计算  妙哉
 *        踩雷了！！！  正数相除会向下取整！！！
 *        因为对于被除数如果时  2的指数次幂  会少算1
 *        如 -10/2 =  [(-9) + (-1)] / 2  =  (-9)/2  + (-1)/2 =-4 + 0 = -4
 *        【正确解法】
 *        -10/2 = -9/2 = -4
 *        -10 - (-4 * 2) = -2
 *        -2/2 = -1
 *        -4 + -2 = -5
 *        -10/2 = (-9/2) + ( -10 - (  (-9/2) * 2)   )/2
 *
 *
 *
 *      4 除数不是系统最小   被除数不是系统最小  统一转成正数经行除法运算
 *
 *      两个正数除法运算
 *      b向左移动7位 此时得到的结果最接近a而不超过a  b如果向左移动8位时  会大于a
 *      设置结果的第7位为1
 *      令  a' = b<<7
 *      a = a - a'
 *      b继续向左移动3位  此时得到的结果最接近a而不超过a  b如果向左移动4位时  会大于a
 *      设置结果的第3位为13
 *
 *      原理   a = (2^甲) * b + (2^乙) * b + (2^丙)  * b + (2^丁) * b
 *
 *
 *
 *
 */
public class Problem_0029_DivideTwoIntegers {


    /**
     * 还是递归版本的好理解呀！！！
     *
     * a+b = (a^b) + (a&b << 1)
     * a异或b 是无进位相加运算  加上  a与b 右移一位得到进位信息
     *
     * @param a
     * @param b
     * @return
     */
    public static int add(int a, int b) {
        if (b == 0) {
            return a;
        }
        return add(a ^ b, (a & b) << 1);
    }



    /**
     * 迭代版本的实现
     * @param a
     * @param b
     * @return
     */
    public static int add2(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }

    /**
     * 求相反数  取反加1  就是相反数
     * @param n
     * @return
     */
    public static int negNum(int n) {
        return add(~n, 1);
    }


    public static int sub(int a, int b) {

        return add(a, negNum(b));
    }


    public static int multi(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return res;
    }


    /**
     * 判断是否为负数
     */
    public static boolean isNeg(int n) {
        // 带符号右移
        //  return ((n>>>31)&1) == 1;
        return n < 0;
    }


    /**
     * 调用这个方法时 要保证 a b不能同时为系统最小
     * @param a
     * @param b
     * @return
     *
     * 用右移的方式  不会产生越界的情况
     */
    public static int div(int a, int b) {
        int x = isNeg(a) ? negNum(a) : a;
        int y = isNeg(b) ? negNum(b) : b;
        int res = 0;
        for (int i = 31; i > negNum(1); i = sub(i, 1)) {
            if ((x >> i) >= y) {
                // 此时时第31位  第30位 第29位  是否为1！！！
                res |= (1 << i);
                x = sub(x, y << i);
            }
        }
        return isNeg(a) ^ isNeg(b) ? negNum(res) : res;
    }



    /**
     * @param dividend  除数
     * @param divisor   被除数
     * @return
     */
    public static int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) {
            return 1;
        } else if (dividend != Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) {
            return 0;
        } else if (dividend == Integer.MIN_VALUE && divisor != Integer.MIN_VALUE) {
            // (-10)/3 = [(-9) + (-1)] / 3  =  (-9)/3  + (-1)/ 3
            // 题目要求  除法溢出返回系统最大
            if(divisor == -1){
                return Integer.MAX_VALUE;
            }
            int res = div(negNum(Integer.MAX_VALUE), divisor);
            return add(res, div(sub(dividend, multi(res, divisor)), divisor));
        } else {
            return div(dividend, divisor);
        }
    }


    /**
     * @param dividend  除数
     * @param divisor   被除数
     * @return
     *
     * 此方法作废  思路有问题！！！
     */
    @Deprecated
    public static int divide1(int dividend, int divisor) {


        if (dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) {
            return 1;
        } else if (dividend != Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) {
            return 0;
        } else if (dividend == Integer.MIN_VALUE && divisor != Integer.MIN_VALUE) {
            // (-10)/3 = [(-9) + (-1)] / 3  =  (-9)/3  + (-1)/ 3
            // 题目要求  除法溢出返回系统最大
            if(divisor == -1){
                return Integer.MAX_VALUE;
            }
            // 这里的解题思路存在漏洞！！！
            // 踩坑地方
            // 如果 被除数divisor=2或-2 时 不能这么算 如 -10/2 = 5
            // 而按照我们的计算方式  (-10)/2 = [(-9) + (-1)] / 2  =  (-9)/2  + (-1)/ 2  = -4

            // return  div(add(dividend, 1), divisor) +  div(-1, divisor);
            // return  div(negNum(Integer.MAX_VALUE), divisor) +  div(-1, divisor);

            // x = (-1)/2 或 (-1)/(-2)
            int x = (divisor == 2) ? -1 : (divisor == -2) ? 1 : 0;
            return add(div(negNum(Integer.MAX_VALUE), divisor), x);
        } else {
            return div(dividend, divisor);
        }
    }


    // teacher
    public static int divide2(int dividend, int divisor) {
        // 被除数是系统最小
        if (divisor == Integer.MIN_VALUE) {
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        }
        // 被除数不是系统最小 除数不是系统最小
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == negNum(1)) {
                return Integer.MAX_VALUE;
            }
            int res = div(add(dividend, 1), divisor);
            return add(res, div(sub(dividend, multi(res, divisor)), divisor));
        }
        // dividend不是系统最小，divisor也不是系统最小
        return div(dividend, divisor);
    }




    public static void main(String[] args) {
        System.out.println(add(-4,6));
        System.out.println(multi(-4,6));

        System.out.println(divide(Integer.MIN_VALUE,-1));
        System.out.println(divide(Integer.MIN_VALUE,2));
        System.out.println(divide2(Integer.MIN_VALUE,-1));
    }




}
