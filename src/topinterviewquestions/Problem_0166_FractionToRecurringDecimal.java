package topinterviewquestions;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Lisp
 * @date： 2021/10/9
 * @version: V1.0
 * @slogan:
 * @description :考点  coding细节
 * 【整体思路】例如 71 / 6
 * 1 先求整数部 71/6 = 11
 * 2 再求小数部分  (71%6)/6 = 5/6  用一个map记录余数出现的位置  5-1
 *   用50/6=8...2   再算2/6    20/6=3...2   余数2即出现位置为 2-2  依次类推
 *   余数开始重复出现时 就代表小数出现了重复
 *
 *
 *
 * 计算机的小数相除实现方式！！！
 *
 *
 */
public class Problem_0166_FractionToRecurringDecimal {


    public static String fractionToDecimal(int numerator, int denominator) {

        if (numerator == 0) {
            return "0";
        }

        // 余数为零 不存在小数部分  踩坑点  系统最小除-1  直接返回会越界 ！！！
//        if (numerator % denominator == 0) {
//            return String.valueOf(numerator / denominator);
//        }

        // 正负 整数部分 包括正负号  必须单独考虑正负号 如 7/(-12)= 0....-5  整数部分不能直接用  7/(-12)得到 因为会丢失负号
        String part1 = (denominator > 0) ^ (numerator > 0) ? "-" : "";

        // 被除数是负数的情况 也要考虑  如 5 / (-8) = 0...(-3)  同时转成long 防止越界
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);

        String part2 = String.valueOf(num / den);

        // 小数部分  有小数部分才需要拼接 ".xyx" 否则不拼接
        long res = num % den;
        if (res == 0) {
            return part1 + part2;
        }

        StringBuffer part3 = new StringBuffer(".");

        Map<Long, Integer> map = new HashMap<>();

        int i = 1;
        // 余数不为零 并且余数不重复
        while (res != 0 && !map.containsKey(res)) {
            part3.append(res * 10 / den);
            map.put(res, i++);
            res = res * 10 % den;
        }

        if (res == 0) {
            // 小数部分为有限位数  如1/5=0.2
            return part1 + part2 + part3.toString();
        }

        // 小数部分为无限循环小数
        int startIndex = map.get(res);
        part3.insert(startIndex, "(").append(")");
        return part1 + part2 + part3.toString();
    }

    public static void main(String[] args) {
        System.out.println(fractionToDecimal(-2,3));

        System.out.println(fractionToDecimal(-71,6));

        System.out.println(fractionToDecimal(-50,6));

        System.out.println(fractionToDecimal(7,-12));
        System.out.println(fractionToDecimal(Integer.MIN_VALUE,-1));


        System.out.println();

        // 0 ...  7
        System.out.println("7 / (-10) = " +(-7) / 10);
        System.out.println("7 % (-10) = " + 7 % (-10));
        // 0 ... -7
        System.out.println("(-7) / 10 = " + (-7) / 10);
        System.out.println("(-7) % 10 = " + (-7) % 10);

        System.out.println(Math.abs(Integer.MIN_VALUE));
    }
}
