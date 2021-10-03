package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/3
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】
 *  自己用TreeNode实现一个队列 而不用系统提供的LinkedList来解答  是因为系统的体统的LinkedList 的节点 对元素 进行了封装
 *  就是说 TreeNode 又被包装成了LinkedList自己的Node会占用额外的空间
 *
 * 整体思路还是利用层序遍历来搞定。  二叉树的层序遍历 就是宽度优先遍历  在双向广度优先的搜索过程中可以利用size来确定一层的节点的个数！！！
 *
 */
public class Problem_0116_PopulatingNextRightPointersInEachNode {

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    /**
     * 模拟实现LinkedList
     */
    public static class MyQueue {
        public int size;
        public Node head;
        public Node tail;

        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * 添加节点
         */
        public void add(Node node) {
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = tail.next;
            }
            size++;
        }

        /**
         * 取出头节点 单独的一个节点  next指针清空
         */
        public Node poll() {
            Node node = head;
            head = head.next;
            node.next = null;
            if (size == 1) {
                tail = null;
            }
            size--;
            return node;
        }
    }

    /**
     *
     * @param root
     * @return
     */
    public static Node connect(Node root) {
        if (root == null){
            return root;
        }
        MyQueue queue = new MyQueue();
        queue.add(root);
        int size = 0;
        while (!queue.isEmpty()){
            size = queue.size;

            // 第一个弹出的节点
            Node pre = null;


            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();

                if (cur.left != null){
                    queue.add(cur.left);
                }
                if (cur.right != null){
                    queue.add(cur.right);
                }

                if (pre != null) {
                    pre.next = cur;
                }
                pre = cur;
            }
            pre.next = null;
        }
        return root;
    }


    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);

        head.right.left = new Node(6);
        head.right.right = new Node(7);
        connect(head);


    }


}
