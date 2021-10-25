package topinterviewquestions;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author ：Lisp
 * @date： 2021/10/19
 * @version: V1.0
 * @slogan:
 * @description :
 * 【思路一】  先要构建父节点的关系图 使用set存其中一个节点的所有父节点，再逐个去遍历另一个节点的父节点
 *
 *  最早的公共祖先节点 必然存在  p q 分别为 x 节点为头节点的左右子树上，或者其中一个是 x节点，另一个x节点的子节点
 *  如果x节点不是p q的最早的公共祖先节点 p q 一定是同时位于 x 的左子树 或者同时位于 x 的右子树
 *
 *【思路二】  利用递归套路
 *
 *
 */
public class Problem_0236_LowestCommonAncestorOfBinaryTree {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }


    /**
     * 使用set存一个节点的所有父节点来找
     */
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        // 构建子节点与父节点关系
        HashMap<TreeNode, TreeNode> parentMap = new HashMap<>();

        TreeNode head = root;
        // 构建子节点父节点关系
        findParent(head, parentMap);

        HashSet<TreeNode> set = new HashSet<>();
        set.add(p);
        while (parentMap.get(p) != null) {
            set.add(parentMap.get(p));
            p = parentMap.get(p);
        }

        while (!set.contains(q)) {
            q = parentMap.get(q);
        }
        return q;
    }


    /**
     * 构建每个子节点 和  父节点的对应关系
     */
    public void findParent(TreeNode node, HashMap<TreeNode, TreeNode> parentMap) {
        if (node.left != null) {
            parentMap.put(node.left, node);
            findParent(node.left, parentMap);
        }
        if (node.right != null) {
            parentMap.put(node.right, node);
            findParent(node.right, parentMap);
        }
    }



    public static class Info {
        public TreeNode ans;//  最早的祖先节点
        public boolean findO1;// 有没有发现过o1
        public boolean findO2;// 有没有发现过o2

        public Info(TreeNode a, boolean f1, boolean f2) {
            ans = a;
            findO1 = f1;
            findO2 = f2;
        }
    }


    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return process(root, p, q).ans;
    }


    /**
     * 当前子树分以下集中情况
     * 1 o1 o2 没有一个在当前节点的子树上
     * 2 o1 或 o2只有一个在当前子树上
     * 3 o1 o2 同时在当前节点的子树上，只有以下三种情况
     *   3.1 o1 o2 各在左右子树上，当前节点x为祖先节点
     *   3.2 o1 o2 同时在当前节点的左子树上或者右子树上
     *   3.3 o1 或者 o2其中一个节点是当前节点，另一个在当前节点的子树上
     */
    public static Info process(TreeNode head, TreeNode o1, TreeNode o2){
        if (head == null) {
            return new Info(null, false, false);
        }


        Info leftInfo = process(head.left, o1, o2);
        Info rightInfo = process(head.right, o1, o2);

        // 是否再当前子树上找到o1节点 3种情况
        boolean findO1 = head == o1 || leftInfo.findO1 || rightInfo.findO1;
        boolean findO2 = head == o2 || leftInfo.findO2 || rightInfo.findO2;

        // 这里的代码高度简介版本的！！！
        // 找到o1 和  o2的最初的交汇点，只可能有以下情况，除此之外，当前子树ans为null
        // 1 在左子树已经提前交汇
        // 2 在右子树已经提前交汇
        // 3 没有在左子树或者右子树提前交汇但是o1 o2已经找全了
        // 4 没有找到最先节点保持ans=null
        TreeNode ans = null;
        if (leftInfo.ans != null) {
            // 先去子树上上找祖先节点，有的话直接作为最低的公共祖先节点
            ans = leftInfo.ans;
        }
        if (rightInfo.ans != null) {
            // 先去子树上上找祖先节点，有的话直接作为最低的公共祖先节点
            ans = rightInfo.ans;
        }
        // 上面的两个if只可能一种成立
        if (ans == null) {
            // 判断当前节点是否为公共节点
            if (findO1 && findO2) {
                ans = head;
            }
        }

        return new Info(ans, findO1, findO2);
    }





}
