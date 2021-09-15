package topinterviewquestions;

import java.util.Stack;

/**
 * @author ：Lisp
 * @date： 2021/9/15
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 使用栈结构  遇到左括号就压入右括号  遇到右括号则从栈中弹出 进行匹配看是否可以匹配的上
 *
 *
 *              这种方案不可行！！！  测试用例："([)]"  中间的括号不能匹配  必须得记录上一次得括号
 *             由拓展思路联想到  使用三个变量 x y z 分别记录是否是合格的  只要x y z其中一个有小于0的情况就是不合条件的
 *             这样可以省去栈的使用！！！
 *
 *
 * 【拓展】如果只有() 一种类型的括号时可以只用一个变量 n  遇到左括号n++  遇到右括号n-- 整个过程中不能存在n<0的情况
 *
 *
 */
public class Problem_0020_ValidParentheses {


    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chs = s.toCharArray();
        for (char ch : chs) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch == '(' ? ')' : (ch == '[' ? ']' : '}'));
            } else {
                if (stack.isEmpty() || ch != stack.pop()) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }



    public static void main(String[] args) {

        System.out.println(isValid("()"));

    }

}
