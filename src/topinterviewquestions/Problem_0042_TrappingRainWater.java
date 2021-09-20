package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/20
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】
 * 接雨水问题  双指针解决 谁小结算谁！！！  需要四个变量  L R maxLeft  maxRight
 * 当前的 i 位置 仅仅依赖与  i L位置左侧的  和 R右侧 较低的一方的值
 *
 * water[i] = max{    (min{ max左, max右}) - height[i], 0     }
 * 当height[i]很高的情况是  减出来的值为负数 直接取0
 *
 *
 */
public class Problem_0042_TrappingRainWater {

    public static int trap(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        int water = 0;
        int L = 1;
        int R = height.length - 2;

        int maxLeft = height[0];
        int maxRight = height[R + 1];
        while (L <= R) {

            if (maxLeft < maxRight) {
                water += Math.max(maxLeft - height[L], 0);
                maxLeft = Math.max(maxLeft, height[L++]);

            } else {
                water += Math.max(maxRight - height[R], 0);
                maxRight = Math.max(maxRight, height[R--]);
            }
        }


        return water;

    }


    public static void main(String[] args) {
        int[] height  = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap(height));
    }
}
