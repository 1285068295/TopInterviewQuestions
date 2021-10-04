package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/4
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0124_BinaryTreeMaximumPathSum {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static class Info{
        public int maxPath;
        public int maxPathFromHead;

        public Info(int maxPath, int maxPathFromHead) {
            this.maxPath = maxPath;
            this.maxPathFromHead = maxPathFromHead;
        }
    }

    public int maxPathSum(TreeNode root) {

        if (root == null){
            return 0;
        }
        return process(root).maxPath;

    }

    /**
     * 分6种情况
     * 1 与x节点无关  左子树的最大路径和
     * 2 与x节点无关  右子树的最大路径和
     * 3 与x节点有关  单独的x节点
     * 4             x节点 + 必须以左子树为头节点一路向下扎的路径和
     * 5             x节点 + 必须以右子树为头节点一路向下扎的路径和
     * 6             x节点 + 必须以左子树为头节点一路向下扎的路径和 + 必须以右子树为头节点一路向下扎的路径和
     */
    private Info process(TreeNode x) {

        if (x == null){
            // 不能返回(0,0) 因为对于树的节点全是负数的情况  最大路径和是最大负数节点
            return null;
        }


        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        int p1 = Integer.MIN_VALUE;
        if (leftInfo!= null){
            p1 = leftInfo.maxPath;
        }

        int p2 = Integer.MIN_VALUE;
        if (rightInfo!= null){
            p2 = rightInfo.maxPath;
        }

        int p3 = x.val;

        int p4 = p3;
        if (leftInfo!= null){
            p4 = p3 + leftInfo.maxPathFromHead;
        }

        int p5 = p3;
        if (rightInfo!= null){
            p5 = p3 + rightInfo.maxPathFromHead;
        }

        int p6 = p3;
        if (leftInfo != null && rightInfo != null) {
            p6 = p3 + leftInfo.maxPathFromHead + rightInfo.maxPathFromHead;
        }


        int maxPath = Math.max(Math.max(p1, Math.max(p2, p3)), Math.max(p4, Math.max(p5, p6)));
        int maxPathFromHead = Math.max(p3, Math.max(p4, p5));
        return new Info(maxPath, maxPathFromHead);
    }


}
