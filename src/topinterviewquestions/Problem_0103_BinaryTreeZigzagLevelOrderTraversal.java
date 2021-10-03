package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/10/3
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【思路】
 *  在102题的基础上改造  使用一个boolean 类型值  每遍历一层  boolean改变依次
 *
 *  根据boolean类型的值来决定当前层是从头到尾正序的从队列中弹出  还是从尾到头倒序的弹出
 *
 *  因为我们只用了一个队列 在弹出元素的时候同时又在添加元素
 *  所以还要根据boolean 类型值 来改变加入队列的元素 是从头加入还是从尾部加入
 *
 *  注意左右子节点的加入顺寻调整
 *
 */
public class Problem_0103_BinaryTreeZigzagLevelOrderTraversal {



    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {

        List<List<Integer>> ans = new ArrayList<>();
        if (root == null){
            return ans;
        }

        // 是否正向遍历的队列
        boolean positive = true;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int size = 0;
        while (!queue.isEmpty()){
            size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = positive ? queue.removeFirst() : queue.removeLast();
                list.add(cur.val);
                if (positive){
                    // 必须要先加左子节点  再加右子节点
                    if (cur.left != null){
                        queue.addLast(cur.left);
                    }

                    if (cur.right != null){
                        queue.addLast(cur.right);
                    }
                }else {

                    // 必须要先加右子节点  再加左子节点
                    if (cur.right != null){
                        queue.addFirst(cur.right);
                    }
                    if (cur.left != null){
                        queue.addFirst(cur.left);
                    }
                }


            }
            ans.add(list);
            positive = !positive;
        }
        return ans;
    }


    public static void main(String[] args) {

        TreeNode head = new TreeNode(1);
        head.left =new TreeNode(2);
        head.right = new TreeNode(3);
        head.left.left = new TreeNode(4);
        head.left.right = new TreeNode(5);
        head.right.left = new TreeNode(6);
        head.right.right = new TreeNode(7);

        zigzagLevelOrder(head);
    }

}
