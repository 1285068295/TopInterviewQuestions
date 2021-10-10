package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/10
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0171_ExcelSheetColumnNumber {


    /**
     * 26 进制的数
     *
     *  这道题反过来也要会写  把数字转换成Excel的序号！！！
     */
    public static int titleToNumber(String columnTitle) {

        int num = 0;
        int t = 1;
        char[] chs = columnTitle.toCharArray();

        // ABC = 1*26*26 + 2*26 + 3
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            num += (chs[i] - 'A' + 1) * t;
            t *= 26;
        }
        return num;

    }




    public static void main(String[] args) {
        String s = "ABC";
        char[] chs = s.toCharArray();
        System.out.println(titleToNumber("AA"));


    }
}
