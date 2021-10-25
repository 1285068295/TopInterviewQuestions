package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/17
 * @version: V1.0
 * @slogan:
 * @description :中序遍历的后继节点
 *
 * moris 中序遍历即可
 * 思路一 因为是二叉搜索树 所以中序遍历是递增的  我们只需要找到第一个比 P 节点值大的数即可
 * 思路二 如果不是二叉搜索树  使用一个节点记录前继节点  当前继节点等于 p 节点时  当前节点就是p 节点的后继节点
 *
 */
public class Problem_0285_InorderSuccessorInBST {

    public static class TreeNode {
        public TreeNode(int val){
            this.val = val;
        }
        public int val;
        public TreeNode left;
        public TreeNode right;
    }


    /**
     * 返回 p 节点的后继节点
     *
     * 假设来到当前节点cur，开始时cur来到头节点位置
     *    1）如果cur没有左孩子，cur向右移动(cur = cur.right)
     *    2）如果cur有左孩子，找到左子树上最右的节点mostRight：
     *    	a.如果mostRight（当前节点的前驱节点）的右指针指向空，让其指向cur  (mostRight.right = cur)
     *    	然后cur向左移动(cur = cur.left)
     *    	b.如果mostRight的右指针指向cur，让其指向null， (mostRight.right = null)
     *    	然后cur向右移动(cur = cur.right)
     *    3）cur为空时遍历停止
     *
     *  思路一 找到第一个比p节点的值大的节点
     *
     *          ┌───1───┐
     *          │       │
     *        ┌─2─┐   ┌─3─┐
     *        │   │   │   │
     *        4   5   6   7
     *
     */
    public static TreeNode inorderSuccessor1(TreeNode head, TreeNode p) {

        if (head == null) {
            return null;
        }

        while (head != null) {
            if (head.left == null) {
                if (head.val > p.val){
                    break;
                }
                head = head.right;
            } else {
                TreeNode mostRight = head.left;
                while (mostRight.right != null && mostRight.right != head){
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null){
                    mostRight.right = head;
                    head = head.left;
                }

                if (mostRight.right == head){

                    if (head.val > p.val){
                        break;
                    }
                    mostRight.right = null;
                    head = head.right;
                }
            }
        }


        return head;

    }


    /**
     * 思路二 使用一个节点记录前继节点
     * @param head
     * @param p
     * @return
     */
    public static TreeNode inorderSuccessor2(TreeNode head, TreeNode p) {

        if (head == null) {
            return null;
        }

        TreeNode preNode = null;

        while (head != null) {
            if (head.left == null) {
                if (preNode == p) {
                    break;
                } else {
                    preNode = head;
                }
                head = head.right;
            } else {
                TreeNode mostRight = head.left;
                while (mostRight.right != null && mostRight.right != head){
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null){
                    mostRight.right = head;
                    head = head.left;
                }

                if (mostRight.right == head){

                    if (preNode == p) {
                        break;
                    } else {
                        preNode = head;
                    }
                    mostRight.right = null;
                    head = head.right;
                }
            }
        }
        return head;
    }

    public static void main(String[] args) {

        TreeNode head = new TreeNode(1);
        head.left =new TreeNode(2);
        head.right = new TreeNode(3);
        head.left.left = new TreeNode(4);
        head.left.right = new TreeNode(5);
        head.right.left = new TreeNode(6);
        head.right.right = new TreeNode(7);


        // 1 2 4 2 5 1 3 6 3 7
        System.out.println(inorderSuccessor2(head, head.right).val);
    }


}




