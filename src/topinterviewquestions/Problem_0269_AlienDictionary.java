package topinterviewquestions;

import java.util.*;

/**
 * @author ：Lisp
 * @date： 2021/10/17
 * @version: V1.0
 * @slogan:
 * @description :
 * 【题目要求】  给你一堆字符串  abc abcd abd 找到组成字符串的字符的字典序
 * 【解题思路】 利用拓扑排序找字典序
 *  如果 甲字符串 "abc" 乙字符串 "abde" 使用两个指针进行比较 比较出第一个不等的字符时 就确定了哪个字符在前
 *  如  abc和 abde比较  c<d 所以c排在前面  而c后面的字符不存在比较意义
 *
 *  构建拓扑结构时  Node 节点直接用Character 即可
 *  图结构的的构建  map  key-character  value(next)- set集合 需要做去重
 *  如  abc abd 可以得到 c<d 而  ac ad 也是得到相同的结果  c<d 没有必要重复计算
 *
 *  这道题很锻炼coding能力  好题！！！
 *
 */
public class Problem_0269_AlienDictionary {

    public static String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }

        int N = words.length;

        // 初始化所有的字符的入度为0  为的是所有的字符都要求出字典顺序  这里的for循环是必不可少的！！！
        HashMap<Character, Integer> inDegree = new HashMap<>();
        for (String word : words) {
            char[] chs = word.toCharArray();
            for (char ch : chs) {
                inDegree.put(ch, 0);
            }
        }


        // 构建图结构  找到所有的字符的next   对比207题目 node节点本包含了 nexts 所以node本身就是图结构
        // 而我们这道题node使用的character 不能作为图结构
        HashMap<Character, HashSet<Character>> graph = new HashMap<>();
        for (int i = 0; i < N - 1; i++) {
            // 相邻的两个字符进行比较  不相邻的没有比较的意义
            char[] cur = words[i].toCharArray();
            char[] nex = words[i + 1].toCharArray();
            int len = Math.min(cur.length, nex.length);
            int j = 0;
            // 注意长的单词不一定排在后面如  aaaaaaa  b
            while (j < len) {
                if (cur[j] != nex[j]) {
                    // 违反了字典序
                    break;
                }
                j++;
            }

            // 跳出while循环  j=len 或者 cur[j] != nex[j]
            if ((j < len && cur[j] > nex[j]) || (j == len && cur.length > nex.length)) {
                // 违反字典序的情况 有两种 1 abd 排在abc前面  2 abb排在ab前面
                return "";
            }
            // 存在字典序 cur[j] < nex[j] 或者 j=len 时 但是 abd 排在abdc前面
            if (j == len && cur.length < nex.length) {
                // 一定是 abc 排在abcd 前面的情况
                continue;
            }

            // 到这里一定时 j!=len并且 cur[j]<nex[j]
            // graph key-字符a  value- a的next集合
            HashSet<Character> next = graph.get(cur[j]);
            if (next == null) {
                next = new HashSet<>();
                graph.put(cur[j], next);
            }
            if (!next.contains(cur[j])) {
                // 重复的字符没有必要计算  如 abc abd 可以得到 c<d 而  ac ad 也是得到相同的结果  c<d 没有必要重复计算
                next.add(nex[j]);
                // a b c 都排在d 前面  d的入度为3
                inDegree.put(nex[j], inDegree.get(nex[j]) + 1);
            }
        }


        // 在所有的inDegree中 找出入度为0的节点
        Deque<Character> zeroInDegree = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegree.add(entry.getKey());
            }
        }


        StringBuffer ans = new StringBuffer("");
        while (!zeroInDegree.isEmpty()) {
            Character ch = zeroInDegree.poll();
            ans.append(ch);
            HashSet<Character> nextChs = graph.get(ch);
            // for (Character nextCh : nextChs)  nextChs为null 会报空指针
            if (nextChs != null) {
                for (Character nextCh : nextChs) {
                    // 找到ch 的next 节点中 下一个入度为0 的节点
                    if (inDegree.get(nextCh) - 1 == 0) {
                        zeroInDegree.add(nextCh);
                    }
                }
            }
        }
        return ans.length() == inDegree.size() ? ans.toString() : "";
    }


    public static void main(String[] args) {

        String[] words1 = {
                "abc",
                "abde",
                "ac",
                "bd"};

        // c<d  b<c a<b

        System.out.println(alienOrder(words1));

        String[] words2 = {
                "abc",
                "abde",
                "ac",
                "bdd",
                "bde"};
        System.out.println(alienOrder(words2));

    }

}
