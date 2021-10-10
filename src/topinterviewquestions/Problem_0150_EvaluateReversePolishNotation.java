package topinterviewquestions;

import java.util.ArrayDeque;

/**
 * @author ：Lisp
 * @date： 2021/10/8
 * @version: V1.0
 * @slogan:
 * @description :
 * 【逆波兰表达式】使用一个栈来处理  遇到数字就压栈  遇到运算符号 弹出栈中数进行处理
 *                再把处理的结果压入栈中  最后弹出栈中结果
 *
 */
public class Problem_0150_EvaluateReversePolishNotation {

    public static int evalRPN(String[] tokens) {
        // 栈结构
        ArrayDeque<String> stack = new ArrayDeque<>();
        for (String token : tokens) {
            if (isOpeator(token)) {
                calculate(stack, token);
            } else {
                stack.push(token);
            }
        }
        return Integer.valueOf(stack.pop());
    }

    private static boolean isOpeator(String token) {
        return "+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token);
    }

    /**
     * 从栈中弹出两个数计算
     * @param stack
     * @param token
     */
    private static void calculate(ArrayDeque<String> stack, String token) {
        int a = Integer.valueOf(stack.pop());
        int b = Integer.valueOf(stack.pop());
        switch (token) {
            case "+":
                stack.push(String.valueOf(b + a));
                break;
            case "-":
                stack.push(String.valueOf(b - a));
                break;
            case "*":
                stack.push(String.valueOf(b * a));
                break;
            case "/":
                stack.push(String.valueOf(b / a));
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        String[] tokens = {"4","13","5","/","+"};
        evalRPN(tokens);
    }

}
