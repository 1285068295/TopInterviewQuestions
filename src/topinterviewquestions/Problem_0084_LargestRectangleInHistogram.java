package topinterviewquestions;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author ：Lisp
 * @date： 2021/10/1
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【解题思路】
 * 利用了单调栈的特性  单调栈可以求数组中距离当前位置 左边最近的比当前位置小（大）的第一个数
 *                                               右边最近的比当前位置小（大）的第一个数
 *
 *  这道题的还有一个巧妙的地方在于 在入栈时如果遇到了 相等的数 直接弹出栈顶相等的数据，可以舍弃弹出的相等的位置形成的矩形的面积
 *  因为下一个压入的相等的数 再次计算形成的最大矩形面积时 一定要比前一个相等的数形成的矩形的面积要大。
 *
 *  这个思想很重要，就是可以舍弃一些可能性，但是舍弃的这些可能性 不会印象我们最终要求的最大值的结果。
 *
 */
public class Problem_0084_LargestRectangleInHistogram {

    public static int largestRectangleArea(int[] heights) {

        // 大数在上 小数在下的栈  栈中存的时数组下表
        // Stack<Integer> stack = new Stack<>();
        // 特别说明  ArrayDeque 测试使用17ms  Stack测试为28ms 猜测原因是 stack 是Vector 子类  效率低
        Deque<Integer> stack = new ArrayDeque<>();
        int max = 0;
        for (int i = 0; i <heights.length; i++) {
            if (stack.isEmpty()){
                stack.push(i);
            }else {
                // 栈不为空 且栈顶数据要比当前数据大  一路弹出栈中数据  弹出的同时结算 当前位置可以形成的最大矩形面积
                while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                    int top = stack.pop();
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    max = Math.max(max, heights[top] * (i - left - 1));
                }
                stack.push(i);
            }
        }
        // 结算栈中数据（从小到大排列的）
        while (!stack.isEmpty()) {
            int top = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek();
            max = Math.max(max, heights[top] * (heights.length - left - 1));
        }
        return max;
    }




    public static void main(String[] args) {
        int[] heights = {              2,1,5,6,2,3};
        System.out.println(largestRectangleArea(heights));
    }
}
