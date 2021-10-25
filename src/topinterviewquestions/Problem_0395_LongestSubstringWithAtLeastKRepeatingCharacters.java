package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/20
 * @version: V1.0
 * @slogan:
 * @description :至少有 K 个重复字符的最长子串
 * 【解题思路】
 *  在子串只有1中字符的情况下 每种字符重复k次时  最长串长度
 *  在子串只有2中字符的情况下 每种字符重复k次时  最长串长度
 *  在子串只有3中字符的情况下 每种字符重复k次时  最长串长度
 *  ...
 *  在子串只有26中字符的情况下 每种字符重复k次时  最长串长度
 *
 *  答案必然是上面情况中的一种 因为题目说了 只有小写字母。  尝试时把字符的种类限制死。
 *
 *  流程如下
 *  使用窗口，窗口向右右扩大  1 种类不足时右扩
 *                          2 种类为指定的种类如 3种字符时，但是每种字符的重复次数小于k 继续右扩
 *                          3 当种类超过指定的次数时 停止右扩  左边界缩小 L 右缩小第一种类达标 且每种字符次数达标停止有所，继续右边界向右扩大
 *
 *
 *  使用欠帐表 + 滑动窗口 来解答，滑动窗口变大时  窗口内的字符的种类保持不变或者增大，具有单调性
 *
 */
public class Problem_0395_LongestSubstringWithAtLeastKRepeatingCharacters {

    /**
     * 时间复杂度O(26*N)
     */
    public int longestSubstring(String s, int k) {
        char[] str = s.toCharArray();
        int N = str.length;
        int max = 0;
        // 计算 在子串只有1 2 3 4 5...中字符的情况下 每种字符重复k次时  最长串长度
        // 遍历字符串的的同时  统计每个字符的数量以及字符的种类
        // 字符种类等于 require 时 但是每个字符的词频小于k 继续右扩
        for (int require = 1; require <= 26; require++) {
            // a~z  a~z 出现次数
            // count[0  1  2]  a b c  出现的次数
            int[] count = new int[26];
            // 目前窗口内收集了几种字符了
            int collect = 0;
            // 目前窗口内出现次数>=k次的字符，满足了几种
            int satisfy = 0;
            // 窗口右边界
            int R = -1;
            // L要尝试每一个窗口的最左位置
            for (int L = 0; L < N; L++) {
                // [L..R]  R+1 R是窗口内的字符  需要观察R+1位置的字符
                // count[str[R + 1] - 'a'] 标识下一个字符的种类  下一个字符右扩进来后种类不会超才向右扩
                //                  下面的逻辑是 右扩进来的字符种类已经达标了加上下一字符后种类就会超 取反表示种类不超或者种类达标了但是下一个字符之前出现过
                // 种类不达标  或者达标时 下一个字符已经出现过
                while (R + 1 < N && !(collect == require && count[str[R + 1] - 'a'] == 0)) {
                    R++;
                    // 下一个字符之前的词频是0 就把种类加1 妙哉！！！ 根据之前出现的次数是否为0 来判断是否是一个新的字符！！！
                    if (count[str[R] - 'a'] == 0) {
                        collect++;
                    }
                    // 词频加上下一个位置的字符后 词频就达标了
                    if (count[str[R] - 'a'] == k - 1) {
                        // 相等时 加1 下次超过词频k时 satisfy 不用更新
                        satisfy++;
                    }
                    // 词频加1
                    count[str[R] - 'a']++;
                }
                // [L...R]

                // 这里存在一个隐含条件  satisfy  ≤ collect
                // 上面的while循环结束 必然是 collect=require 再往下扩种类就超了  此时satisfy是 词频为k时才会加1
                // 就表示当前窗口内 满足了种类为require要求的种类 且 每个要求的字符种类词频都是k(至少)
                if (satisfy == require) {
                    max = Math.max(max, R - L + 1);
                }
                // L++ 左边界缩小  需要正确的更新窗口内字符的种类 以及达标的字符
                if (count[str[L] - 'a'] == 1) {
                    collect--;
                }
                if (count[str[L] - 'a'] == k) {
                    satisfy--;
                }
                count[str[L] - 'a']--;
            }
        }
        return max;
    }



}
