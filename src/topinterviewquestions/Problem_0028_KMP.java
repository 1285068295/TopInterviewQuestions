package topinterviewquestions;

import java.util.ArrayList;

/**
 * @author ：Lisp
 * @date： 2021/9/19
 * @version: V1.0
 * @slogan:
 * @description :
 * 【KMP算法拓展】
 *  【题目】
 *  	给定两棵二叉树的头节点head1和head2
 *  	想知道head1中是否有某个子树的结构和head2完全一样
 *
 *  【解答】
 *  先把两棵树进行先序方式序列化为数组，注意序列化过程没有子节点的节点后面补上null进行占位操作，
 *  然后判断序列化后的数组是否包含另一个序列化的数组
 *
 *  写过一遍KMP再写这个顺手了不少呀！
 *
 *
 * 【题目】
 * 判断两个字符字符串是否互为旋转字符串
 * 如“123456”  的旋转字符串有  “234561” “345612”“456123” “561234”
 * 如 123456与561234
 *
 * 解法  123456拼接自己得到  123456123456  判断561234是否为123456123456的子串
 *
 *
 */
public class Problem_0028_KMP {


    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }


    // KMP算法实现
    public static boolean containsTree1(Node big, Node small){
        // 注意big small为空的情况处理
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }

        ArrayList<Node> str1 = new ArrayList<>();
        ArrayList<Node> str2 = new ArrayList<>();

        preSerialize(big, str1);// 序列化
        preSerialize(small, str2);
        return contains(str1, str2);
    }



    /**
     * 递归实现前序方式序列化二叉树
     */
    private static void preSerialize(Node head, ArrayList<Node> str) {
        if (head == null) {
            str.add(null);
            return;
        }
        str.add(head);
        preSerialize(head.left, str);
        preSerialize(head.right, str);
        return;
    }

    /**
     * KMP算法判断 一个数组是否包含另一个数组
     */
    private static boolean contains(ArrayList<Node> str1, ArrayList<Node> str2) {

        int[] nextArray = getNext(str2);// 生成next数组
        int i =0;// str1比较的元素
        int j =0;// str2比较的元素
        while (i < str1.size() && j < str2.size()) {
            if (equals(str1.get(i), str2.get(j))) {
                i++;
                j++;
            } else if (j > 0) {// 这里不能有=0的情况
                // 注意临接条件 j=0 next[j]=-1 再次进入while循环时 get(j)报错
                j = nextArray[j];// 用str1[i]与str2[next[j]]
            } else {
                // j=0的情况，此时j不动i++
                i++;
            }
        }
        return j == str2.size() ? true : false;
    }



    /**
     * 对str生成next数组
     */
    private static int[] getNext(ArrayList<Node> str) {
        // 人为规定前两项
        if (str.size() == 1) {
            return new int[] { -1 };
        }

        if (str.size() == 2) {
            return new int[] { -1, 0 };
        }
        int length = str.size();
        int[] next = new int[length];
        int i = 2;
        int m = next[i - 1];
        while (i < length) {
            // 要比较的两个字符 直接用str[i-1] str[m]
            // 计算当前的第i个字符时 是要计算第i个字符的前面的字符串 不包含第i个字符本身！！！
            // 比较的是i-1 与 next[i-1]
            if (equals(str.get(i-1), str.get(m))) {// 要比较的是i-1 与next[i-1]
                next[i] = m + 1;
                m = next[i];// 先更新m 注意m=next[i]或者m++一样   再更新i 方便下次while循环的时候使用
                i++;
            } else if (m > 0) {// m = next[i-1]
                m = next[m];// 前缀的 前缀！！！  注意临接条件  m=0 next[m]=-1 再次进入while循环时 get(m)报错
            } else {
                next[i] = 0;
                // m = next[i];// 更新m 注意m=next[i] 此时的m=0 不需要再设置m
                i++;
            }
        }
        return next;
    }



    /**
     * 判断是否相等
     * 参数有可能为null
     */
    private static boolean equals(Node node1, Node node2) {
        if (node1 == null && node2 != null) {
            return false;
        }
        if (node1 != null && node2 == null) {
            return false;
        }

        if (node1 == null && node2 == null) {
            return true;
        }

        if (node1.value != node2.value) {
            return false;
        }
        return true;
    }






    // 递归的方式
    public static boolean containsTree2(Node big, Node small) {
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }
        if (isSameValueStructure(big, small)) {
            return true;
        }
        return containsTree1(big.left, small) || containsTree1(big.right, small);
    }

    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

















    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
            Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containsTree1(big, small);
            boolean ans2 = containsTree2(big, small);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }




}
