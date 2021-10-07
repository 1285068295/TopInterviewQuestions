package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/10/7
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 详解参见139题  利用前缀树进行加速
 */
public class Problem_0140_WordBreakII {


    /**
     * 暴力递归解决方式
     */
    public static List<String>  wordBreak1(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>(wordDict);
        List<String> ans = new ArrayList<>();
        LinkedList<String> path = new LinkedList<>();
        process1(s, 0, set, path, ans);
        return ans;
    }

    /**
     * str 从index ... 可以切分的单词的方案
     */
    public static void process1(String str, int index, HashSet<String> set, LinkedList<String> path, List<String> ans) {
        if (index == str.length()) {
            StringBuffer sb = new StringBuffer();
            for (String s : path) {
                sb.append(s).append(" ");
            }
            ans.add(sb.toString().trim());
            return;
        }
        for (int i = index; i < str.length(); i++) {
            if (set.contains(str.substring(index, i + 1))) {
                path.add(str.substring(index, i + 1));
                process1(str, i + 1, set, path, ans);
                path.removeLast();
            }
        }
    }




    /**
     * 使用前缀树加速 代替set
     */
    public static List<String>  wordBreak2(String s, List<String> wordDict) {
        // 先构建一个根节点
        Node1 root = new Node1();
        // 构建整个字典树
        for (String str : wordDict) {
            // 每个单词都要从root根节点开始构建
            Node1 cur = root;
            char[] chs = str.toCharArray();
            for (int i = 0; i < chs.length; i++) {
                int index = chs[i] - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node1();
                }
                cur = cur.nexts[index];
            }
            cur.end = true;
        }

        List<String> ans = new ArrayList<>();
        LinkedList<String> path = new LinkedList<>();
        process2(s.toCharArray(), 0, root, path, ans);
        return ans;
    }

    /**
     * str 从index ... 可以切分的单词的方案数量
     */
    public static void process2(char[] str, int index,  Node1 root, LinkedList<String> path, List<String> ans) {
        if (index == str.length) {
            StringBuffer sb = new StringBuffer();
            for (String s : path) {
                sb.append(s).append(" ");
            }
            ans.add(sb.toString().trim());
            return;
        }
        // 每次都要从头开始计算
        Node1 curNode = root;
        for (int i = index; i < str.length; i++) {

            int curIndex = str[i] - 'a';
            // 小加速
            if (curNode.nexts[curIndex] == null) {
                break;
            }
            // 利用前缀树进行巧妙的加速
            if (curNode.nexts[curIndex] != null && curNode.nexts[curIndex].end) {
                String split = String.valueOf(str, index, i - index + 1);
                path.add(split);
                Node1 pre = curNode;
                // 再次调用process2 要从前缀树的根部重新判断
                process2(str, i + 1, root, path, ans);
                path.removeLast();

            }

            // 终极踩坑点 写在了else中
            // 踩坑点 这里要移动node节点  curIndex写成了index
            curNode = curNode.nexts[curIndex];
        }
    }

    /**
     * 经典的只有小写字母的字典树实现
     */
    public static class Node1 {
        // end表示 当前字符是否为一个单词的结尾 默认为false
        public boolean end;
        // 前缀树结构  字典树路径判断本质用是否为null来判断的
        // 默认为null 需要使用null来判断是否下一个节点
        public Node1[] nexts;

        public Node1() {
            nexts = new Node1[26];
        }
    }





    // 以下代码为老师的答案


    /**
     *  前缀树  使用了path来存完整单词
     */
    public static class Node {
        public String path;
        public boolean end;
        public Node[] nexts;

        public Node() {
            path = null;
            end = false;
            nexts = new Node[26];
        }
    }

    public static List<String> wordBreak(String s, List<String> wordDict) {
        char[] str = s.toCharArray();
        Node root = gettrie(wordDict);
        boolean[] dp = getdp(s, root);
        ArrayList<String> path = new ArrayList<>();
        List<String> ans = new ArrayList<>();
        process(str, 0, root, dp, path, ans);
        return ans;
    }

    public static void process(char[] str, int index, Node root, boolean[] dp, ArrayList<String> path,
                               List<String> ans) {
        if (index == str.length) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < path.size() - 1; i++) {
                builder.append(path.get(i) + " ");
            }
            builder.append(path.get(path.size() - 1));
            ans.add(builder.toString());
        } else {
            Node cur = root;
            for (int end = index; end < str.length; end++) {
                int road = str[end] - 'a';
                if (cur.nexts[road] == null) {
                    break;
                }
                cur = cur.nexts[road];
                if (cur.end && dp[end + 1]) {
                    path.add(cur.path);
                    process(str, end + 1, root, dp, path, ans);
                    path.remove(path.size() - 1);
                }
            }
        }
    }

    public static Node gettrie(List<String> wordDict) {
        Node root = new Node();
        for (String str : wordDict) {
            char[] chs = str.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.path = str;
            node.end = true;
        }
        return root;
    }

    public static boolean[] getdp(String s, Node root) {
        char[] str = s.toCharArray();
        int N = str.length;
        boolean[] dp = new boolean[N + 1];
        dp[N] = true;
        for (int i = N - 1; i >= 0; i--) {
            Node cur = root;
            for (int end = i; end < N; end++) {
                int path = str[end] - 'a';
                if (cur.nexts[path] == null) {
                    break;
                }
                cur = cur.nexts[path];
                if (cur.end && dp[end + 1]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp;
    }






    public static void main(String[] args) {

        String s1 = "leetcode";
        ArrayList<String> wordList1 = new ArrayList<>();
        wordList1.add("leet");
        wordList1.add("code");


        String s2 = "catsanddog";
        ArrayList<String> wordList2 = new ArrayList<>();
        wordList2.add("cat");
        wordList2.add("cats");
        wordList2.add("and");
        wordList2.add("sand");
        wordList2.add("dog");



        wordBreak2(s2, wordList2);
    }

}
