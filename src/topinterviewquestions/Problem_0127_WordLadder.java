package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/10/5
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 解题思路  使用了双向广度优先遍历
 * 踩坑点 abc  ->   xyz 不能单纯的认为  abc和xyz有多少个不同字符就需要走几步
 * 反例：
 * 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * 输出：5
 * 解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。
 *
 * 先把所有的路径放到set结合中
 * beginWord = hit 第一个字母从a变到z(不包括h)  ait bit cit dit ... zit 看下这些字符串那些出现在了路径set集合中
 * 就是beginWord的下一个点的路径。
 * 从找到路径中再改变第二个字母 i  如ait    从a变到z(不包括i)  aat abt act...azt 看下这些字符串那些出现在了路径set集合中
 *                              如bit    从a变到z(不包括i)  bat bbt bct...bzt 看下这些字符串那些出现在了路径set集合中
 *
 * 双向遍历时  总是在一层的节点的数量少的一层取遍历
 * 参见 Problem_0045_JumpGameIIFollowUpOnClass_DBSF_双向广度优先遍历.jpg
 *
 *
 *
 */
public class Problem_0127_WordLadder {


    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {

        HashSet<String> path = new HashSet<>();
        for (String s : wordList) {
            path.add(s);

        }
        if (!path.contains(endWord)) {
            return 0;
        }

        int len = 1;
        // 终止条件时 在startSet的节点走向下一个点路径出现在endSet中就终止搜索 所以要使用set集合
        HashSet<String> startSet = new HashSet<>();
        HashSet<String> endSet = new HashSet<>();
        HashSet<String> visit = new HashSet<>();

        startSet.add(beginWord);
        endSet.add(endWord);

        while (!startSet.isEmpty()) {
            HashSet<String> nextLevel = new HashSet<>();
            // 遍历一层的节点
            for (String str : startSet) {
                char[] chs = str.toCharArray();
                // startStr到下一个字符串的路径  hit  尝试ait bit cit ... zit
                for (int i = 0; i < chs.length; i++) {
                    char tmp = chs[i];
                    for (int j = 0; j < 26; j++) {
                        chs[i] = (char) ('a' + j);
                        if (chs[i] == tmp) {
                            continue;
                        } else {
                            String next = String.valueOf(chs);

                            if (endSet.contains(next)){
                                // 踩坑点  这里的返回需要加1
                                return len + 1;
                            }

                            // abc -> bbc   在计算bbc存在aac的路径 为了做到去重不走重复路 使用visit
                            if (path.contains(next) && !endSet.contains(next) && !visit.contains(next)) {
                                nextLevel.add(next);
                                // 这里踩坑  需要加入str
                                visit.add(str);
                            }
                        }
                    }
                    // 必须得还原hit呀
                    chs[i] = tmp;
                }
            }
            if (nextLevel.size() > endSet.size()) {
                startSet = endSet;
                endSet = nextLevel;
            } else {
                startSet = nextLevel;
            }
            len++;
        }
        // 踩坑点 这里不能返回len 因为没有找到路径所以要返回0
        return 0;
    }


    /**
     * teacher coding
     */
    public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return 0;
        }
        HashSet<String> startSet = new HashSet<>();
        HashSet<String> endSet = new HashSet<>();
        HashSet<String> visit = new HashSet<>();
        startSet.add(beginWord);
        endSet.add(endWord);
        for (int len = 2; !startSet.isEmpty(); len++) {
            HashSet<String> nextSet = new HashSet<>();
            for (String w : startSet) {
                for (int j = 0; j < w.length(); j++) {
                    char[] ch = w.toCharArray();
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c != w.charAt(j)) {
                            ch[j] = c;
                            String next = String.valueOf(ch);
                            if (endSet.contains(next)) {
                                return len;
                            }
                            if (dict.contains(next) && !visit.contains(next)) {
                                nextSet.add(next);
                                visit.add(next);
                            }
                        }
                    }
                }
            }
            startSet = (nextSet.size() < endSet.size()) ? nextSet : endSet;
            endSet = (startSet == nextSet) ? endSet : nextSet;
        }
        return 0;
    }

    public static void main(String[] args) {
//        String beginWord = "a",endWord = "c";
//        List<String> wordList = new ArrayList<>();
//        wordList.add("a");
//        wordList.add("b");
//        wordList.add("c");

//        String beginWord = "hit", endWord = "cog";
//        List<String> wordList = new ArrayList<>();
//        wordList.add("hot");
//        wordList.add("dot");
//        wordList.add("dog");
//        wordList.add("lot");
//        wordList.add("log");
//        wordList.add("cog");


        String beginWord = "hot", endWord = "dog";
        List<String> wordList = new ArrayList<>();
        wordList.add("hot");
        wordList.add("dog");
        System.out.println(ladderLength(beginWord, endWord, wordList));
    }

}
