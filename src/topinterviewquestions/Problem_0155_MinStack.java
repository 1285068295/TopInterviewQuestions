package topinterviewquestions;

import java.util.ArrayDeque;

/**
 * @author ：Lisp
 * @date： 2021/10/9
 * @version: V1.0
 * @slogan:
 * @description :
 * 【思路】 使用两个栈每压栈一个数 就更新最小栈中对应的最小值
 *
 *
 * 在leetcode上提交时，把文字替换成下面的代码
 * 然后把类名、构造方法名从Problem_0155_MinStack改为MinStack即可
 */
public class Problem_0155_MinStack {

    private ArrayDeque<Integer> numStack;
    private ArrayDeque<Integer> minStack;


    public Problem_0155_MinStack() {
        numStack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public void push(int val) {
        numStack.push(val);
        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            int top = minStack.peek();
            minStack.push(val < top ? val : top);
        }
    }

    /**
     * 弹出时同时弹出
     */
    public void pop() {
        minStack.poll();
        numStack.pop();
    }

    public int top() {
        return numStack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
