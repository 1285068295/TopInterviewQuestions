package topinterviewquestions;

import java.util.HashMap;

/**
 * @author ：Lisp
 * @date： 2021/10/5
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【解题思路】
 *  使用一个map维持每个数结尾的数字有几个数
 *
 */
public class Problem_0128_LongestConsecutiveSequence {

    public static int longestConsecutive(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        // 3 5 6 4
        // 3-1
        // 3-1 5-1
        // 3-1 5-1  先加入6-1 看下有没有5-1结尾的有数量为1即5-5 更新5和6数量  5-2 6-2
        // map 3-1 5-2 6-2 先加入4-1
        // 有3结尾的1个  5结尾的2个  区间段表示为    [3-3]  4 [5 6]
        // 此时只更新3 6  3-4  6-4 中间的4 5作为垃圾数据不用管了
        int len = 0;
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
                int preLen = map.containsKey(num - 1) ? map.get(num - 1) : 0;
                int posLen = map.containsKey(num + 1) ? map.get(num + 1) : 0;
//                int preLen = map.getOrDefault(num - 1, 0);
//                int posLen = map.getOrDefault(num + 1, 0);

                int all = preLen + posLen + 1;
                map.put(num - preLen, all);
                map.put(num + posLen, all);
                len = Math.max(len, all);
            }
        }
        return len;
    }


    public static void main(String[] args) {
        int[] arr = {3,4,6,5};
        longestConsecutive(arr);
    }

}
