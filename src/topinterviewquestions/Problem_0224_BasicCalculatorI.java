package topinterviewquestions;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author ：Lisp
 * @date： 2021/10/19
 * @version: V1.0
 * @slogan:
 * @description :  224 227 770 772 一个系列的计算器
 *
 * 【整体递归思路如下】
 *  递归函数遇到右括号时或者字符串终止时 计算结果返回
 *  遇到左括号时调用递归处理剩下的部分
 *
 *  返回值是一个数组
 *  第一个值位当前递归函数处理完成表达式后的结果只
 *  第二个值位遇到右括号或者字符串结尾时 i的索引
 *
 *
 */
public class Problem_0224_BasicCalculatorI {


    public static int calculate(String s) {
        return f(s.toCharArray(), 0)[0];
    }

    /**
     * 递归函数遇到右括号时或者字符串终止时 计算结果返回
     * 遇到左括号时调用递归处理剩下的部分
     *
     * 返回值是一个数组
     * 第一个值位当前递归函数处理完成表达式后的结果只
     * 第二个值位遇到右括号或者字符串结尾时 i的索引
     * @param str
     * @param i
     * @return
     */
    private static int[] f(char[] str, int i) {
        Deque<String> stack = new ArrayDeque<>();
        int curNum = 0;
        int[] bar = null;
        while (i < str.length && str[i] != ')') {
            if (str[i] == ' ') {
                i++;
                continue;
            }

            if (str[i] >= '0' && str[i] <= '9') {
                curNum = curNum * 10 + str[i++] - '0';
            } else if (str[i] != '(') {
                // 遇到是运算符号  加号或者减号直接加入栈中  乘除时从栈中弹出一个数 计算完后在压栈
                // 这里的处理逻辑是 遇到运算符号时 先去处理上一个运算符号  这样就不用获取运算符号的下一个数了
                // 因为从当前的运算符号往下 可能存在空格 不好获得下一个数
                addNum(stack, curNum);
                stack.push(String.valueOf(str[i++]));
                // 踩坑地方 要重置为0
                curNum = 0;
            } else {
                // 利用递归去计算 (...) 中表达式值
                bar = f(str, i+1);
                // 踩坑地方  这里直接将bar[0]赋值即可  不需要再压栈了！！！
                curNum = bar[0];
                i = bar[1];
            }
        }
        addNum(stack, curNum);
        return new int[]{getNum(stack), i + 1};
    }

    /**
     * 处理栈中的运算表达式的结果
     * 只有加减运算
     * @param stack
     * @return
     */
    private static int getNum(Deque<String> stack) {
        // 处理栈中的运算
        int ans = 0;
        while (!stack.isEmpty()) {
            int nextNum = Integer.valueOf(stack.poll());
            // 兼容最后一个数前面没有符号
            String symbol = stack.isEmpty() ? "+" : stack.poll();
            ans += "+".equals(symbol) ? nextNum : (-nextNum);
        }
        return ans;
    }

    /**
     *  根据num 前面的运算符号来决定是否要进行计算
     */
    private static void addNum(Deque<String> stack, int num) {
        if (!stack.isEmpty()) {
            String preSymbol = stack.poll();
            if (stack.isEmpty() || preSymbol.equals("+") || preSymbol.equals("-")) {
                stack.push(preSymbol);
            } else {
                int preNum = Integer.valueOf(stack.poll());
                // * / 运算
                num = preSymbol.equals("*") ? preNum * num : preNum / num;
            }
        }
        stack.push(String.valueOf(num));
    }


    public static void main(String[] args) {
//        System.out.println(calculate("-3*2"));
        System.out.println(calculate("(-3)*2"));
    }


}
