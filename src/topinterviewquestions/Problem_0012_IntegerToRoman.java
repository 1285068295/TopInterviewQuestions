package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/7
 * @version: V1.0
 * @slogan:
 * @description :阿拉伯数字转罗马数字
 *
 */
public class Problem_0012_IntegerToRoman {

    public static String intToRoman(int num) {
        // 把 0~9  10~90  100~900  1000~3000做成二维表
        // 罗马数字没有0的表示
        String[][] c = {
                { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" },
                { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" },
                { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" },
                { "", "M", "MM", "MMM" } };
        StringBuilder roman = new StringBuilder();
        roman
                .append(c[3][num / 1000 % 10])
                .append(c[2][num / 100 % 10])
                .append(c[1][num / 10 % 10])
                .append(c[0][num % 10]);
        return roman.toString();
    }


    public static void main(String[] args) {
        System.out.println(intToRoman(120));
    }



}
