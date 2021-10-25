package topinterviewquestions;

import java.util.*;

/**
 * @author ：Lisp
 * @date： 2021/10/12
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 参考 140 139 使用前缀树进行加速
 */
public class Problem_0212_WordSearchII {

    /**
     * 经典的只有小写字母的字典树实现
     *
     * 哈哈  老师的代码只用了5ms ！！！ 我的居然81ms！！！
     *
     * 老师的做法与我的做法时间的差距原因分析
     *
     * 如words={"abc", "abd"}
     *               c
     *               b
     *          d  b a  b  c
     *               b
     *               d
     * 如果沿途已经走过了abc abd 剩下的重复路径 abc abd 不需要重复再走了  而我设计的时还会重复走的 这就体现出时间上的差距了。
     *
     */
    public static class Node {
        // 最后的节点存放完整单词 为了方便收集答案使用
        public String word;

        // end表示 当前字符是否为一个单词的结尾 默认为false
        // 为了省去收集答案时用的set 将boolean end改为int类型 只有end>0才收集答案
        public int end;
        // 前缀树结构  字典树路径判断本质用是否为null来判断的
        // 默认为null 需要使用null来判断是否下一个节点
        public Node[] nexts;

        public Node() {
            nexts = new Node[26];
        }
    }


    public static List<String> findWords1(char[][] board, String[] words) {

        // 这里有一个小加速
        HashSet<String> set = new HashSet<>();

        // 先构建一个根节点
        Node root = new Node();
        // 构建整个字典树
        for (String word : words) {
            if (!set.contains(word)) {
                // 每个单词都要从root根节点开始构建
                Node cur = root;
                char[] chs = word.toCharArray();
                for (int i = 0; i < chs.length; i++) {
                    int index = chs[i] - 'a';
                    if (cur.nexts[index] == null) {
                        cur.nexts[index] = new Node();
                    }
                    cur = cur.nexts[index];
                }
                cur.end++;
                cur.word = word;
                set.add(word);
            }
        }

        int N = board.length;
        int M = board[0].length;
        // 这里存在瑕疵  就是从不同的出发点 收集了相同的答案 用set去重 效率低不如老师的做法妙！！！
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < N ; i++) {
            for (int j = 0; j < M; j++) {
                process(board, N, M, i, j, root, ans);
            }
        }
        return ans;
    }

    /**
     * 利用前缀树 加速递归过程 注意为了不走重复路 注意需要恢复现场
     * @param board
     * @param N
     * @param M
     * @param i
     * @param j
     * @param root
     * @param ans
     */
    private static void process(char[][] board, int N, int M, int i, int j, Node root,  List<String> ans) {
        // 越界处理 重复路径
        if (i < 0 || j < 0 || i == N || j == M || board[i][j] == 0) {
            return;
        }

        int index = board[i][j] - 'a';
        if (root.nexts[index] == null) {
            // 后面路径没有必要尝试
            return;
        }

        if (root.nexts[index] != null && root.nexts[index].end > 0) {
            root.nexts[index].end--;
            ans.add(root.nexts[index].word);
            // 踩坑点 这里不能return了 因为后面可能还有单词呢 如ab abc第二个单词也要收集的
        }
        if (root.nexts[index] != null) {
            // 为了不走重复路
            char tmp = board[i][j];
            board[i][j] = 0;
            process(board, N, M, i + 1, j, root.nexts[index], ans);
            process(board, N, M, i - 1, j, root.nexts[index], ans);
            process(board, N, M, i, j + 1, root.nexts[index], ans);
            process(board, N, M, i, j - 1, root.nexts[index], ans);
            board[i][j] = tmp;
        }
    }







    //  下面为老师的答案  用pass去重 实在时妙哉！！！

    public static class TrieNode {
        // 最后的节点存放完整单词 为了方便收集答案使用
        public String word;

        public TrieNode[] nexts;
        public int pass;
        public int end;

        public TrieNode() {
            nexts = new TrieNode[26];
            pass = 0;
            end = 0;
        }
    }

    public static List<String> findWords(char[][] board, String[] words) {
        TrieNode head = new TrieNode(); // 前缀树最顶端的头
        HashSet<String> set = new HashSet<>();
        for (String word : words) {
            if (!set.contains(word)) {
                fillWord(head, word);
                set.add(word);
            }
        }
        // 答案
        List<String> res = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                // 枚举在board中的所有位置
                // 每一个位置出发的情况下，答案都收集
                process(board, row, col,  head, res);
            }
        }
        return res;
    }

    public static void fillWord(TrieNode node, String word) {
        node.pass++;
        char[] chs = word.toCharArray();
        int index = 0;
        for (int i = 0; i < chs.length; i++) {
            index = chs[i] - 'a';
            if (node.nexts[index] == null) {
                node.nexts[index] = new TrieNode();
            }
            node = node.nexts[index];
            node.pass++;
        }
        node.end++;
        node.word = word;
    }

    // 从board[row][col]位置的字符出发，
    // 之前的路径上，走过的字符，记录在path里
    // cur还没有登上，有待检查能不能登上去的前缀树的节点
    // 如果找到words中的某个str，就记录在 res里
    // 返回值，从row,col 出发，一共找到了多少个str
    public static int process(char[][] board, int row, int col, TrieNode cur,
                              List<String> res) {
        char cha = board[row][col];
        if (cha == 0) { // 这个row col位置是之前走过的位置
            return 0;
        }
        // (row,col) 不是回头路

        int index = cha - 'a';
        // 如果没路，或者这条路上最终的字符串之前加入过结果里
        if (cur.nexts[index] == null || cur.nexts[index].pass == 0) {
            return 0;
        }
        // 没有走回头路且能登上去
        cur = cur.nexts[index];
        int fix = 0; // 从row和col位置出发，后续一共搞定了多少答案
        // 当我来到row col位置，如果决定不往后走了。是不是已经搞定了某个字符串了
        if (cur.end > 0) {
            res.add(cur.word);
            cur.end--;

            fix++;
        }
        // 往上、下、左、右，四个方向尝试
        board[row][col] = 0;
        if (row > 0) {
            fix += process(board, row - 1, col,  cur, res);
        }
        if (row < board.length - 1) {
            fix += process(board, row + 1, col,  cur, res);
        }
        if (col > 0) {
            fix += process(board, row, col - 1,  cur, res);
        }
        if (col < board[0].length - 1) {
            fix += process(board, row, col + 1,  cur, res);
        }
        // 需要恢复现场
        board[row][col] = cha;
        cur.pass -= fix;
        return fix;
    }


    public static void main(String[] args) {

//        char[][] board = {{'o', 'a', 'a', 'n'}, {'e', 't', 'a', 'e'}, {'i', 'h', 'k', 'r'}, {'i', 'f', 'l', 'v'}};
//        String[] words = {"oath", "pea", "eat", "rain"};

        char[][] board = {
                {'o', 'a', 'b', 'n'},
                {'o', 't', 'a', 'e'},
                {'a', 'h', 'k', 'r'},
                {'a', 'f', 'l', 'v'}};
        String[] words = {"oa", "oaa"};
        findWords(board, words);



    }

}



