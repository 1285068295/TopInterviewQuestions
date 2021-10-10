package topinterviewquestions;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author ：Lisp
 * @date： 2021/10/10
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】
 *  把数字当作字符串处理 拼接生成字典序最大的字符串
 *
 *  先对字符串进行排序  a b两个字符串排序策略时  ab(a字符串拼接b字符串) > ba(b字符串拼接a字符串) 时 a排前面
 *
 *  【错误的解题思路】
 *   直接按照字符串的字典序进行排序时不对的
 *   字典序的定义  如果两个字符串长度相等 直接比较即可，如果两个字符串的长度不相等，把短的字符串拼接到与长的字符串的
 *   长度相等后再进行比较
 *   如 ab和 c比较   等价于  ab  c0 比较  显然  ab<c0
 *
 *   对于直接按照字典序排序后 再把字符串拼接起来的方案是不对的  反例
 *   ba和b 按照字典序排序后  b<ba 得到结果位  bba  不如  bab 的字典序小
 *
 *   正确的比较策略为  ba和b拼接后  与  b和ba拼接后  ——> bba和bab比较 bab<bba 所以ba放在前面  b放在后面
 *
 *
 *  如果最终得到的排序结果为
 *     ... a   m1   m2   b ...
 *     ... a   m1   m2   b ...
 *  <  ... m1  a    m2   b ...
 *  <  ... m1  m2   a    b ...
 *  <  ... m1  m2   b    a ...
 *  <  ... m1   b   m2   a ...
 *  <  ... b   m1   m2   a ...  每次都只交换两个字符串位置
 *
 *  归纳法可以证明  最终的排序结果  任意一个前面字符串拼接后面的字符串后 < 后面的字符串拼接前面的字符串
 */
public class Problem_0179_LargestNumber {


    public static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return (o2 + o1).compareTo(o1 + o2);
        }

    }

    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(strs, new MyComparator());
        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            builder.append(str);
        }
        String ans = builder.toString();
        char[] str = ans.toCharArray();
        int index = -1;
        for (int i = 0; i < str.length; i++) {
            if (str[i] != '0') {
                index = i;
                break;
            }
        }
        return index == -1 ? "0" : ans.substring(index);
    }

}
