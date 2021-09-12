package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/6
 * @version: V1.0
 * @slogan:
 * @description :整数反转
 * 【技巧】 转换为负数来搞  负数的区间要比正数的区间大
 *  -2147483648
 *   2147483647
 *  -Integer.MIN_VALUE  = Integer.MIN_VALUE = -2147483648
 *
 * 再举一例，我们来看整数-1在计算机中如何表示。
 * 假设这也是一个int类型，那么：
 * 1、先取1的原码： 00000000 00000000 00000000 00000001
 * 2、得反码：     11111111 11111111 11111111 11111110
 * 3、得补码：     11111111 11111111 11111111 11111111
 *
 */
public class Problem_0007_ReverseInteger {
    public static void main(String[] args) {
        System.out.println(-Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
        long a = (long) Math.pow(2,31);
        System.out.println(a);

        long b = (long) Math.pow(2,31) - 1;
        System.out.println(b);

        System.out.println(reverse(-1200));

        System.out.println(((-1 >>> 31) & 1) == 1);
        

    }

    /**
     * 一律把正数转换为负数去处理  因为负数的范围比正数大1
     * @param x
     * @return
     */
    public static int reverse(int x) {
            // x是否为正数
            boolean neg = ((x >>> 31) & 1) == 1;
            // 统一转为负数处理  负数不变  正数变成负数再处理
            x = neg ? x : -x;
            int m = Integer.MIN_VALUE / 10;
            int o = Integer.MIN_VALUE % 10;

            int res = 0;
            while(x != 0){
                // 检测是否溢出
                if (res < m || (res == m && x % 10 < o)) {
                    return 0;
                }
                // 上面的if就是为了下面这行不溢出
                res = res * 10 + x % 10;
                x /= 10;
            }
            return neg ? res : Math.abs(res);

    }

}
