package topinterviewquestions;

import java.util.LinkedList;

/**
 * @author ：Lisp
 * @date： 2021/10/3
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【判断是否镜像树的思路】
 *  迭代方法  层序遍历来搞
 *  递归方法  当节点相等时  用当前节点的左子树  和  当前节点的右子树 判断是否时镜像的
 *
 *
 */
public class Problem_0101_SymmetricTree {
   public static class TreeNode {
       int val;
       TreeNode left;
       TreeNode right;
       TreeNode() {}
       TreeNode(int val) { this.val = val; }
       TreeNode(int val, TreeNode left, TreeNode right) {
           this.val = val;
           this.left = left;
           this.right = right;
       }
   }


    /**
     *            ┌────1────┐
     *            │         │
     *         ┌──2──┐   ┌──2──┐
     *         │     │   │     │
     *         4   ┌─5─┐ 5   ┌─4─┐
     *             │   │     │   │
     *             8   9     8   9
     */
    public static boolean isSymmetric(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();

        if (root == null){
            return true;
        }

        queue.add(root);

        while (!queue.isEmpty()){
            int size = queue.size();

            LinkedList<TreeNode> levelNode = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (cur.left != null){
                    queue.add(cur.left);
                }
                if (cur.right != null){
                    queue.add(cur.right);
                }
                // 判断下一层的节点是否对称
                levelNode.add(cur.left);
                levelNode.add(cur.right);
            }
            //  levelNode中存了null的节点
            while(!levelNode.isEmpty()){
                if (!equals(levelNode.removeFirst(),levelNode.removeLast())){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否相等
     */
    private static boolean equals(TreeNode first, TreeNode last) {
        if (first == null && last == null){
            return true;
        }

        if (first == null && last != null){
            return false;
        }

        if (first != null && last == null){
            return false;
        }

        return first.val == last.val;
    }







    /**
     * 老师给的最优解
     */
    public static boolean isSymmetric2(TreeNode root) {
        return isMirror(root, root);
    }

    /**
     * 判断以head1为头节点的树和以head2为头节点的树 是否是镜像的
     * @return
     */
    private static boolean isMirror(TreeNode head1, TreeNode head2) {
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1 != null && head2 != null) {
            return head1.val == head2.val
                    && isMirror(head1.left, head2.right)
                    && isMirror(head1.right, head2.left);
        }
        // 一个为空，一个不为空  false
        return false;
    }






    public static void main(String[] args) {

        TreeNode head = new TreeNode(1);
        head.left =new TreeNode(2);
        head.right = new TreeNode(2);
//        head.left.left = new TreeNode(3);
        head.left.right = new TreeNode(4);
        head.right.left = new TreeNode(4);
//        head.right.right = new TreeNode(3);
//        System.out.println(isSymmetric(head));
        System.out.println(isSymmetric2(head));

    }



}
