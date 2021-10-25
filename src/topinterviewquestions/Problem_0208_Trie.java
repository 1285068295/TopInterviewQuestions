package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/11
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0208_Trie {

    /**
     * 前缀树的节点
     */
    public class Node{
        // nexts用的的node 而不是char  这里是踩坑点
        public Node[] nexts;
        // end 标识是否是一个单词的结尾
        public boolean end;
        public Node(){
            nexts = new Node[26];
        }
    }

    /**
     * 前缀树的根节点
     */
    private Node root;

    public Problem_0208_Trie() {
        root = new Node();
    }

    public void insert(String word) {
        Node cur = root;
        char[] chs = word.toCharArray();
        for (char ch : chs) {
            int index = ch - 'a';
            if (cur.nexts[index] == null){
                cur.nexts[index] = new Node();
            }
            cur = cur.nexts[index];
        }
        // 单词的结束标识
        cur.end = true;
    }

    public boolean search(String word) {

        Node cur = root;
        char[] chs = word.toCharArray();
        for (char ch : chs) {
            int index = ch - 'a';
            if (cur.nexts[index] == null){
                // 提前中断了 不存在单词
                return false;
            }
            cur = cur.nexts[index];
        }
        // 单词的结束标识
        return cur.end == true;

    }

    public boolean startsWith(String prefix) {
        Node cur = root;
        char[] chs = prefix.toCharArray();
        for (char ch : chs) {
            int index = ch - 'a';
            if (cur.nexts[index] == null){
                // 提前中断了 不存在单词
                return false;
            }
            cur = cur.nexts[index];
        }
        // 单词的结束标识
        return true;
    }
}
