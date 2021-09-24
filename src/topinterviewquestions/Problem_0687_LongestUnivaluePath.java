package topinterviewquestions;

import jdk.internal.org.objectweb.asm.tree.TableSwitchInsnNode;

import javax.swing.tree.TreeNode;

/**
 * @author ：Lisp
 * @date： 2021/9/24
 * @version: V1.0
 * @slogan:
 * @description :
 * leetcode 687
 * 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
 *
 * 注意：两个节点之间的路径长度由它们之间的边数表示。
 *               5
 *              / \
 *             4   5
 *            / \   \
 *           1   1   5
 * 输出2
 *
 * 【解题思路】 二叉树的递归套路
 * 分情况
 * 1 与x节点无关情况下  max{左子树的最大路径, 右子树最大路径}
 * 2 与x节点有关情况下
 *      2.1 只有x节点自己
 *      2.2  x=x.left   x节点自己+左子树 必须以左子节点出发时的最大路径
 *      2.3  x=x.left   x节点自己+右子树 必须以左子节点出发时的最大路径
 *      2.4  x=x.left&&x=x.right    x节点自己+左子树+右子树 必须以左/右子节点出发时的最大路径
 */
public class Problem_0687_LongestUnivaluePath {


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


    public static class Info {
        // 必须从x节点出发情况下的最大长度
        public int len;
        // x为头节点整个子树的最大长度（可能经过x节点，也可能不经过）
        public int max;

        public Info(int len, int max) {
            this.len = len;
            this.max = max;
        }
    }

    public static int longestUnivaluePath(TreeNode root) {
        if (root == null){
            return 0;
        }
        return process(root).max -1;


    }

    /**
     * 返回以x节点为头节点的子树的 最长路径的节点个数
     * @param x
     * @return
     *
     * 递归的套路的精髓：在收集info信息时按照 与x有关  与x无关来求解！！！
     *
     */
    private static Info process(TreeNode x) {
        if (x == null){
            return new Info(0,0);
        }

        Info lInfo = process(x.left);
        Info rInfo = process(x.right);

        // 是在求len max 时按照与x无关情况下和与x有关情况下来求len max
        // 注意不是按照与x无关情况下和与x有关情况的len max来讨论
        int len = 1;
        int max = 1;

        if(x.left != null && x.val == x.left.val){
            len = lInfo.len + 1;
        }

        if (x.right !=null && x.val == x.right.val){
            len = Math.max(len, rInfo.len + 1);
        }


        // max的值求解
        // 与x无关的情况
        // 1 x左子树max
        // 2 x右子树max


        // 与x有关的情况
        // 3 x自己
        // 4 必须从x出发的len
        // 5 必须从x出发的len
        // 6 同时向左伸展+向右伸展 = 左子树len+右子树len+x
        max = Math.max(Math.max(lInfo.max, rInfo.max), len);
        if (x.left != null && x.right != null && x.val == x.left.val && x.val == x.right.val) {
            max = Math.max(max, lInfo.len + rInfo.len + 1);
        }
        return new Info(len, max);
    }


}
