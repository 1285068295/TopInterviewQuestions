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
 * 【二叉树层序遍历套路】
 *  记录每层的节点的个数  每次从队列中从前往后的弹出时 弹出该层指定的个数的节点节点就是当前层的所有节点。
 */
public class Problem_0102_BinaryTreeLevelOrderTraversal {


    public class TreeNode {
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


    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> ans = new ArrayList<>();
        if (root == null){
            return ans;
        }

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int size = 0;
        while (!queue.isEmpty()){
            size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                list.add(cur.val);
                if (cur.left != null){
                    queue.add(cur.left);
                }

                if (cur.right != null){
                    queue.add(cur.right);
                }
            }
            ans.add(list);
        }
        return ans;
    }
}
