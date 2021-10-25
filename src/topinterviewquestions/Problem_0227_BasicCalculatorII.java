package topinterviewquestions;

import java.util.LinkedList;

/**
 * @author ：Lisp
 * @date： 2021/10/18
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【解题思路】 正宗的解答方式参考224题  这道题不考虑 括号运算符号
 *
 *  很锻炼代码coding能力！！！ 多写写！！！
 *
 */
public class Problem_0227_BasicCalculatorII {

    public static int calculate(String s) {
        char[] str = s.toCharArray();
        LinkedList<String> list = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        builder.setLength(0);
        for (int i = 0; i < str.length; i++) {
            if (str[i] != ' ') {
                if (str[i] >= '0' && str[i] <= '9') {
                    builder.append(str[i]);
                } else {
                    handleStack(list, builder.toString(), str[i]);
                    builder.setLength(0);
                }
            }
        }
        handleStack(list, builder.toString(), ' ');
        return computeStack(list);
    }

    public static void handleStack(LinkedList<String> list, String str, char op) {
        if (list.isEmpty() || (list.peekLast().equals("+") || list.peekLast().equals("-"))) {
            list.addLast(str);
        } else {
            int num = Integer.valueOf(str);
            String preOp = list.pollLast();
            int preNum = Integer.valueOf(list.pollLast());
            if (preOp.equals("*")) {
                list.addLast(String.valueOf(preNum * num));
            } else {
                list.addLast(String.valueOf(preNum / num));
            }
        }
        list.addLast(String.valueOf(op));
    }

    public static int computeStack(LinkedList<String> list) {
        int ans = Integer.valueOf(list.pollFirst());
        while (list.size() != 1) {
            String op = list.pollFirst();
            int cur = Integer.valueOf(list.pollFirst());
            if (op.equals("+")) {
                ans += cur;
            } else {
                ans -= cur;
            }
        }
        return ans;
    }

}
