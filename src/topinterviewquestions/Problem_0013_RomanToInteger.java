package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/8
 * @version: V1.0
 * @slogan:
 * @description :罗马数字包含以下七种字符: I， V， X， L，C，D 和 M
 *
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000

 *【解题思路】
 * 如果当前字符代表的值不小于其右边，就加上该值；否则就减去该值。
 * 如 IV 表示 4  否则就是一个罗马数字
 *
 */
public class Problem_0013_RomanToInteger {

    public static int romanToInt(String s) {
        int nums[] = new int[s.length()];
        // 先把每个字符转成对应的数字
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'M':
                    nums[i] = 1000;
                    break;
                case 'D':
                    nums[i] = 500;
                    break;
                case 'C':
                    nums[i] = 100;
                    break;
                case 'L':
                    nums[i] = 50;
                    break;
                case 'X':
                    nums[i] = 10;
                    break;
                case 'V':
                    nums[i] = 5;
                    break;
                case 'I':
                    nums[i] = 1;
                    break;
                default:
                    break;
            }
        }
        int sum = 0;
        // 规则很简单
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] < nums[i + 1]) {
                sum -= nums[i];
            } else {
                sum += nums[i];
            }
        }
        // for循环没有计算最后一位
        return sum + nums[nums.length - 1];
    }

    public static void main(String[] args) {

        int num = romanToInt("LVIII");


    }
}
