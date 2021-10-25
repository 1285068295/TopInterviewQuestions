package topinterviewquestions;

import java.util.LinkedList;

/**
 * @author ：Lisp
 * @date： 2021/10/21
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0297_SerializeAndDeserializeBinaryTree {


    /**
     *     提交代码时不要提交TreeNode类
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            val = value;
        }
    }


    /**
     *  层序的方式进行序列化
     *  输入：root = [1,2,3,null,null,4,5]
     *  输出：[1,2,3,null,null,4,5]
     */
    public static String serialize(TreeNode root) {

        // 先用list暂存node 节点 为的是去除末尾的null
        LinkedList<TreeNode> list = new LinkedList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        while(!queue.isEmpty()){
            TreeNode curNode = queue.poll();
            if (curNode == null){
                list.add(null);
            } else {
                list.add(curNode);
                if (curNode.left != null) {
                    queue.addLast(curNode.left);
                } else {
                    queue.addLast(null);
                }
                if (curNode.right != null) {
                    queue.addLast(curNode.right);
                } else {
                    queue.addLast(null);
                }
            }
        }
        // 1,2,3,null,null,4,5,null,null,null,null,
        // 去除末尾的null
        while (!list.isEmpty() && list.peekLast() == null) {
            list.removeLast();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        TreeNode node = list.pollFirst();
        builder.append(node == null ? "null" : node.val + "");

        while (!list.isEmpty()) {
            TreeNode tmp = list.pollFirst();
            builder.append(tmp == null ? ",null" : "," + tmp.val);
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * 层序的方式进行反序列化
     *
     * 错误的思路  层序不能使用递归来生成  如下
     *
     * 0 1 2 3    4    5 6 7 8
     * 1,2,3,null,null,4,5,6,7
     *
     *          ┌─────1─────┐
     *          │           │
     *        ┌─2─┐       ┌─3─┐
     *        │   │       │   │
     *       null null  ┌─4─┐ 5
     *                  │   │
     *                  6   7
     *
     *  原因这不是一棵满二叉树！！！ 不能利用索引对应关系来构建二叉树！！！
     *
     */
    @Deprecated
    public static TreeNode deserialize2(String data) {
        if (data == null || data.equals("[]")){
            return null;
        }
        String[] strs = data.substring(1, data.length()-1).split(",");
        return process(strs, 0);
    }



    public static TreeNode  process(String[] strs, int i){
        if (i >= strs.length) {
            return null;
        }

        TreeNode parent = generateTreeNode(strs[i]);

        // 构建左右子树
        TreeNode leftChild = process(strs, i * 2 + 1);
        TreeNode rightChild = process(strs, i * 2 + 2);

        if (parent != null) {
            parent.left = leftChild;
        }
        if (parent != null) {
            parent.right = rightChild;
        }
        return parent;
    }


    private static TreeNode generateTreeNode(String val) {
        return  val.equals("null") ? null : new TreeNode(Integer.valueOf(val));
    }


    /**
     * 利用队列来构建
     *
     * 测试用力就是个坑！！！
     */
    public static TreeNode deserialize(String data) {
        String[] strs = data.substring(1, data.length() - 1).split(",");
        LinkedList<TreeNode> queue = new LinkedList<>();
        int i = 0;
        TreeNode head = generateTreeNode((strs[i++]));
        if (head == null) {
            return null;
        }

        queue.addLast(head);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                TreeNode leftChild = generateTreeNode(i == strs.length ? "null" : strs[i++]);
                node.left = leftChild;
                queue.addLast(leftChild);

                TreeNode rightChild = generateTreeNode(i == strs.length ? "null" : strs[i++]);
                node.right = rightChild;
                queue.addLast(rightChild);
            }
        }
        return head;
    }




    public static void main(String[] args) {

        TreeNode head = new TreeNode(1);
        head.left = new TreeNode(2);
        head.right = new TreeNode(3);
        head.right.left = new TreeNode(4);
        head.right.right = new TreeNode(5);
        System.out.println(serialize(head));


        TreeNode root = deserialize("[1,2,3,null,null,4,5,6,7]");
    }


}
