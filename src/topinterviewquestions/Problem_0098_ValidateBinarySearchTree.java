package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/3
 * @version: V1.0
 * @slogan:
 * @description :
 * Moris中序遍历  当前数是否总是比前一个数大
 *
 */
public class Problem_0098_ValidateBinarySearchTree {

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
    public static void Moris(TreeNode head){
        while (head != null){
            if (head.left == null){
                System.out.println(head.val);
                head =head.right;
            }else {

                TreeNode mostRight = head.left;
                while (mostRight.right != null && mostRight.right != head){
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null){

                    mostRight.right = head;
                    System.out.println(head.val);
                    head = head.left;

                }

                if (mostRight.right == head){
                    mostRight.right = null;
                    System.out.println(head.val);
                    head = head.right;

                }
            }
        }


    }

    /**
     * 先序遍历  对于经过两次的节点第一次遍历到时处理  第二次时不处理
     *
     * 中序遍历  对于经过两次的节点第二次遍历到时处理  第一次时不处理
     */
    public static boolean isValidBST(TreeNode head) {
        Integer pre = null;
        while (head != null) {
            if (head.left == null) {
                if (pre == null || head.val > pre) {
                    pre = head.val;
                } else {
                    return false;
                }
                head = head.right;
            } else {

                TreeNode mostRight = head.left;
                while (mostRight.right != null && mostRight.right != head) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = head;
                    head = head.left;

                }

                if (mostRight.right == head) {
                    mostRight.right = null;
                    if (pre == null || head.val > pre) {
                        pre = head.val;
                    } else {
                        return false;
                    }
                    head = head.right;

                }
            }
        }
        return true;
    }

}
