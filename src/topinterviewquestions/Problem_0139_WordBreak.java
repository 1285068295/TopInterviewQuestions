package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/10/6
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】
 *  利用了131拆分字符串的递归思路来求解 把str的前缀拆分出来  剩下的字符串取跑递归。
 *  【特别注意】  对于字符串特别长的情况 在set中查找是否存在时  时间复杂度不能认为时O(1) 应该时O(K)
 *               K为字符串的长度，在计算字符串的hashcode时 需要遍历一遍字符串！！！
 *
 *  这道题的加速在于使用前缀树进行加速。
 *
 *
 *
 *
 */
public class Problem_0139_WordBreak {

    /**
     * 暴力递归解决方式
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>(wordDict);
        return process(s, 0, set) != 0;

    }

    /**
     * str 从index ... 可以切分的单词的方案数量
     *
     * @param str
     * @param index
     * @param set
     * @return
     */
    public static int process(String str, int index, HashSet<String> set) {
        if (index == str.length()) {
            return 1;
        }
        int ways = 0;
        for (int i = index; i < str.length(); i++) {
            if (set.contains(str.substring(index, i + 1))) {
                ways += process(str, i + 1, set);
            }
        }
        return ways;
    }




    /**
     * 暴力递归改动态规划版本 版本2
     *
     * 时间复杂度分析  首先双重for循环已经是 O(N*2)  set.contains(s) 也是字符串长度 （计算hashcode造成的）
     * 所以总的时间复杂度为O(N*3)
     *
     * 居然跑完了！！！
     */
    public static boolean wordBreak2(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>(wordDict);
        int N = s.length();
        // dp[i] 表示 i~N 可以拆分的方案数量
        int[] dp = new int[N+1];
        // baseCase
        dp[N] = 1;
        for (int i = N; i >= 0; i--) {
            for (int index = i; index < s.length(); index++) {
                if (set.contains(s.substring(i, index + 1))) {
                    dp[i] += dp[index + 1];
                }
            }
        }
        return dp[0] != 0;
    }





    /**
     * 暴力递归该动态规划版本 版本3  easy game!!!
     *
     * 时间复杂度分析  首先双重for循环已经是 O(N*2)  set.contain(s) 也是字符串长度 （计算hashcode造成的）
     * 所以总的时间复杂度为O(N*3)
     * 利用把字典放到前缀树中 来检查拆分的单词是否存在 代替set.contains  将时间复杂度降低一阶
     */
    public static boolean wordBreak3(String s, List<String> wordDict) {
        // 先构建一个根节点
        Node root = new Node();
        // 构建整个字典树
        for (String str : wordDict) {
            // 每个单词都要从root根节点开始构建
            Node cur = root;
            char[] chs = str.toCharArray();
            for (int i = 0; i < chs.length; i++) {
                int index = chs[i] - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];
            }
            cur.end = true;
        }

        char[] str = s.toCharArray();
        int N = str.length;
        // dp[i] 表示 i~N 可以拆分的方案数量
        int[] dp = new int[N+1];
        // baseCase
        dp[N] = 1;
        for (int i = N; i >= 0; i--) {
            Node curNode = root;
            for (int index = i; index < N; index++) {
                int curIndex = str[index] - 'a';
                // 小加速
                if (curNode.nexts[curIndex] == null) {
                    break;
                }
                // 利用前缀树进行巧妙的加速
                if (curNode.nexts[curIndex] != null && curNode.nexts[curIndex].end) {
                    dp[i] += dp[index + 1];
                }
                // 踩坑点 这里要移动node节点  curIndex写成了index
                curNode = curNode.nexts[curIndex];
            }
        }
        return dp[0] != 0;
    }




    /**
     * 经典的只有小写字母的字典树实现
     */
    public static class Node {
        // end表示 当前字符是否为一个单词的结尾 默认为false
        public boolean end;
        // 前缀树结构  字典树路径判断本质用是否为null来判断的
        // 默认为null 需要使用null来判断是否下一个节点
        public Node[] nexts;

        public Node() {
            nexts = new Node[26];
        }
    }






    public static void main(String[] args) {

        String s = "leetcode";
        ArrayList<String> wordList = new ArrayList<>();
        wordList.add("leet");
        wordList.add("code");

        System.out.println(wordBreak3(s, wordList));


    }
}
