package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/7
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 用两个指针 从左和右边同时开始遍历   谁小结算谁
 *
 */
public class Problem_0011_ContainerWithMostWater {

    /**
     * teacher
     * @param h
     * @return
     */
    public static int maxArea2(int[] h) {
        int max = 0;
        int l = 0;
        int r = h.length - 1;
        while (l < r) {
            max = Math.max(max, Math.min(h[l], h[r]) * (r - l));
            if (h[l] > h[r]) {
                r--;
            } else {
                l++;
            }
        }
        return max;
    }


    public static int maxArea(int[] h) {
        if(h == null || h.length == 0){
            return 0;
        }
        int l = 0;
        int r = h.length - 1;

        int max = 0;
        while(l <r){
            int cur = 0;
            if (h[l] < h[r]) {
                cur = h[l] * (r - l);
                l++;
            } else {
                cur = h[r] * (r - l);
                r--;
            }
            max =Math.max(max, cur);
        }
        return  max;

    }


}
