package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/19
 * @version: V1.0
 * @slogan:
 * @description : Moris中序遍历找
 *
 * 先序遍历是 经过两次的节点 第一次时就打印
 * 中序遍历是 经过两次的节点 第二次时才打印
 */
public class Problem_0230_KthSmallestElementInBST {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public static int kthSmallest(TreeNode head, int k) {
        if (head == null){
            return -1;
        }
        while(head != null && k != 0){
            if (head.left == null){
                if (--k==0){
                    break;
                }

                head = head.right;

            }else {
                TreeNode mostRight = head.left;
                while (mostRight.right != null && mostRight.right != head){
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null){
                    // 第一次为空时 设置右指针后 继续向左走
                    mostRight.right = head;
                    head = head.left;
                }
                if (mostRight.right == head){
                    if (--k==0){
                        break;
                    }
                    // 第二次 应该向右走了
                    mostRight.right = null;
                    head = head.right;
                }
            }
        }
        return head.val;
    }
}