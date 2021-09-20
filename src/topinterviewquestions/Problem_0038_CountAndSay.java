package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Lisp
 * @date： 2021/9/20
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 第一项是数字 1
 * 描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
 * 描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
 * 描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
 * 描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
 *
 * 【思路】 有点类似斐波那契数列
 *
 */
public class Problem_0038_CountAndSay {


    /**
     * 3322251  ->  2个3 3个2 1个5 1个1 ->23321511
     * @param n
     * @return
     *
     * 效率很低  跟老师的解法一样呀！！！
     */
    public static String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }

        String pre = "1";
        for (int i = 1; i < n; i++) {
            char[] chs = pre.toCharArray();

            String next  ="";
            for (int j = 0; j < chs.length; j++) {
                int times = 1;
                while (j+1 < chs.length && chs[j] == chs[j + 1]) {
                    times++;
                    j++;
                }
                next += times + ""+ (chs[j]-'0');
                pre = next;
            }

        }
        return pre;
    }






    //  teacher
    public static String countAndSay2(int n) {
        if (n < 1) {
            return "";
        }
        if (n == 1) {
            return "1";
        }
        char[] last = countAndSay2(n - 1).toCharArray();
        StringBuilder ans = new StringBuilder();
        int times = 1;
        for (int i = 1; i < last.length; i++) {
            if (last[i - 1] == last[i]) {
                times++;
            } else {
                ans.append(String.valueOf(times));
                ans.append(String.valueOf(last[i - 1]));
                times = 1;
            }
        }
        ans.append(String.valueOf(times));
        ans.append(String.valueOf(last[last.length - 1]));
        return ans.toString();
    }












    public static void main(String[] args) {
        System.out.println(countAndSay(6));
    }

}
