package topinterviewquestions;

import java.util.HashMap;

/**
 * @author ：Lisp
 * @date： 2021/10/3
 * @version: V1.0
 * @slogan:
 * @description : 从前序与中序遍历序列构造二叉树
 * 【解题思路】
 * 设计一个递归函数  f(pre, L1, R1, in, L2, R2)
 * pre 前序遍历的结果集
 * L1 前序遍历的结果集的索引为L1位置
 * 中 前序遍历的结果集的索引为R1位置
 * in 中序遍历的结果集
 * L2 中序遍历的结果集的索引为L2位置
 * R2 中序遍历的结果集的索引为R2位置
 * f函数 ：利用pre前序遍历结果集在区间段 [L1, R1]  in 中序遍历结果集在区间段 [L2, R2] 结果集构建一颗二叉树
 *
 *
 *
 *
 */
public class Problem_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal {

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
    public static TreeNode buildTree(int[] preorder, int[] inorder) {

        HashMap<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i],i);
        }
        return f(preorder, 0 , preorder.length -1, inorder, 0, inorder.length -1, indexMap);


    }


    /**
     * @param preorder
     * @param L1
     * @param R1
     * @param inorder
     * @param L2
     * @param R2
     * @param indexMap
     * @return
     */
    private static TreeNode f(int[] preorder, int L1, int R1, int[] inorder, int L2, int R2, HashMap<Integer, Integer> indexMap) {
        // preorder 和 inorder是等长的
        if (L1 > R1) {
            return null;
        }

        TreeNode head = new TreeNode(preorder[L1]);

        if (L1 == R1) {
            return head;
        }

        // 找到头节点在inorder数组的中的位置 就可以根据其所在位置  其两侧就是左子树和右子树
        int findIndex = indexMap.get(preorder[L1]);

        // 左子树的节点个数  findIndex-L2  右子树的节点个数 R2-findIndex
        // 递归构建左子树
        head.left = f(preorder, L1 + 1, L1 + findIndex - L2, inorder, L2, findIndex - 1, indexMap);
        // 递归构建右子树
        head.right = f(preorder, L1 + findIndex - L2 + 1, R1, inorder, findIndex + 1, R2, indexMap);
        return head;
    }


}
