package topinterviewquestions;

import java.util.*;

/**
 * @author ：Lisp
 * @date： 2021/10/1
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【解题思路】
 *  二叉树的中序遍历  moris遍历 时间复杂度O(N) 空间复杂度O(1)
 * 二叉树Moris遍历
 * 时间复杂度为O(N) 空间复杂度为O(1)
 *
 * 遍历规则（只有叶子节点的右指针会被改变！！！）
 * 假设来到当前节点cur，开始时cur来到头节点位置
 *  1）如果cur没有左孩子，cur向右移动(cur = cur.right)
 *  2）如果cur有左孩子，找到左子树上最右的节点mostRight：
 *  	a.如果mostRight（当前节点的前驱节点）的右指针指向空，让其指向cur  (mostRight.right = cur)
 *  	然后cur向左移动(cur = cur.left)
 *  	b.如果mostRight的右指针指向cur，让其指向null， (mostRight.right = null)
 *  	然后cur向右移动(cur = cur.right)
 *  3）cur为空时遍历停止
 *
 *  在直到Moris遍历顺序的基础上去改造先序遍历  后续遍历   中序遍历！！！
 *  Moris遍历的顺序就是在每次移动cur前打印cur节点
 *
 *  任何一个节点只有其有左子节点 一定会来到两次！！！
 *
 *  Moris遍历的过程中一个节点会打印两次的根本原因是因为  cur.left != null
 *  对于cur.left == null cur=cur.right，没有左子树 直接右移了  只会打印一次
 *
 *   2021-10-03 Moris遍历的理解
 *   第一次的遍历节点是 把沿途左子树叶子节点的右指针指向自己。  注意while循环时 head=head.left head=head.right 改变的时机
 *   观察图可知  左子树的叶子节点会遍历两次
 *
 *
 *  参见Moris顺序图
 *
 *  关于二叉树的层序遍历的重要知识点  使用队列 + 每遍历一层节点数量  就能完美解决二叉树的层序遍历！！！
 *
 */
public class Problem_0094_BinaryTreeInorderTraversal {


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


    /**
     * 方案1 暴力递归  深度优先
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        process(root, ans);
        return ans;
    }

    private static void process(TreeNode root, List<Integer> ans) {

        if (root == null){
            return;
        }
        process(root.left, ans);
        ans.add(root.val);
        process(root.right,ans);
    }


    /**
     * 中序遍历
     * 使用连个栈作为辅助，利用了前序遍历的结果
     *      1
     *	  2   3
     *   4 5 6 7
     *
     *  4 5 2 6 7 3 1
     * 迭代法中序遍历（左 - 中 - 右）50
     * 依然使用栈作为辅助结构来中序遍历，只要子节点就一直压栈，
     * 记住：终止条件为（1）栈不为空（2）遍历的当前节点不为空。
     *     整体的遍历为：只要当前遍历的节点不为空就一直压入栈  一个大的   if(左子节点不为空) - else(右子节点不为空)
     *
     *
     *  只要当前节点不为空 就一路沿着左子树向下扎  遇到空节点时，从栈中弹出一个节点，处理当前节点
     *  当前节点就是整棵树的最左的节点，令head=head.right 再进行一路向左子树扎
     *
     */
    public static List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root == null) {
            return ans;
        }

        // root不为空 就一路沿着左子树进行压栈
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                // 当前节点为空时 从栈中弹出一个节点
                // 栈中弹出的节点的顺序一定是 左节点-中节点  弹出完成这两个节点后  才令root=root.right
                // 就是说左节点 中节点已经处理完了  才处理的右节点！！！
                TreeNode node = stack.pop();
                ans.add(node.val);
                // 切换到右节点 继续一路向左扎
                root = node.right;
            }
        }
        return ans;
    }



    /**
     * 前序遍历
     */
    public static void pre(TreeNode head){
        if (head == null) {
            return;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(head);
        while(!stack.isEmpty()){

            TreeNode top = stack.pop();
            System.out.println(top.val);
            // 注意要先压入右侧子节点  再压入左侧子节点 整个流程类似层序遍历
            if(top.right != null){
                stack.push(top.right);
            }
            if(top.left != null){
                stack.push(top.left);
            }
        }
    }

    /**
     * 后序遍历
     * 使用连个栈作为辅助，利用了前序遍历的结果
     * 	    1
     *	  2   3
     *   4 5 6 7
     */
    public static void pos1(TreeNode head){
        if(head == null){
            return;
        }

        // 不要直接使用Stack类  效率低
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(head);
        Deque<TreeNode> help = new ArrayDeque<>();
        while(!stack.isEmpty()){

            TreeNode top = stack.pop();
            // help作为辅助结构，前序遍历的结果存下来，弹出就是后序遍历结果
            help.push(top);
            // 注意要先压入左侧子节点  再压入左侧子节点 整个流程类似层序遍历
            if(top.left != null){
                stack.push(top.left);
            }
            if(top.right != null){
                stack.push(top.right);
            }
        }
        // help中存的结果是前序遍历结果，弹出得到后序遍历结果
        while (!help.isEmpty()) {
            System.out.println(help.pop().val);
        }

    }



    //**************************下面代码是Moris遍历相关的代码  start************************************


    /**
     * Moris遍历
     *   ┌───1───┐
     *   │       │
     * ┌─2─┐   ┌─3─┐
     * │   │   │   │
     * 4   5   6   7
     *
     * Moris遍历  每次在移动cur时打印cur节点，可知遍历的顺序为
     * Moris顺序为: 1 2 4 2 5 1 3 6 3 7
     * 只在第一遇到当前节点时打印当前节点  就是先序遍历1 2 4 x 5 x 3 6 x 7
     *
     * 二叉树的前 中  后 都是在Moris遍历的基础上修改得到的  （笔试阶段可以不写 面试聊时  装逼必备神器。）
     *
     *
     */
    public static void Morris(TreeNode head){
        TreeNode cur =head;

        while(cur !=null){
            if(cur.left == null){// 没有左子树 直接右移
                // 没有左子树  只会来到自己一次的节点
                System.out.print(cur.val + " ");
                cur = cur.right;
            }else{// cur.left != null
                TreeNode mostRight = cur.left;
                // 跳出循环有两种情况
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    // 移动之前打印当前节点
                    System.out.print(cur.val + " ");
                    // cur左移
                    cur = cur.left;
                }
                if(mostRight.right == cur){
                    mostRight.right = null;
                    // 移动之前打印当前节点
                    System.out.print(cur.val + " ");
                    // cur右移
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }



    /**
     * 先序遍历
     * 注意观察Moris遍历的顺序
     * Moris遍历  每次在移动cur时打印cur节点，可知遍历的顺序为
     * Moris顺序为: 1 2 4 2 5 1 3 6 3 7
     *
     * 只在第一遇到当前节点时打印当前节点  第二次回到自己时不打印即为先序遍历
     * 就是先序遍历1 2 4 x 5 x 3 6 x 7
     *
     * @see #Morris 方法
     */
    public static void MorrisPre(TreeNode head){
        while(head != null){
            if (head.left == null){
                System.out.print(head.val + " ");
                head = head.right;
            } else {
                TreeNode mostRight = head.left;
                while (mostRight.right != null &&  mostRight.right != head){
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null){
                    System.out.print(head.val + " ");
                    mostRight.right = head;
                    head = head.left;
                }

                if (mostRight.right == head){
                    // 第二次来到的节点  观察Moris顺序遍历.jpg
                    mostRight.right = null;
                    System.out.print("x ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }


    /**
     * 中序遍历
     *
     * 注意观察Moris遍历的顺序
     * Moris遍历  每次在移动cur时打印cur节点，可知遍历的顺序为
     * Moris顺序为: 1 2 4 2 5 1 3 6 3 7
     *
     * 对于只在Moris顺序出现了依次的节点直接打印，出现了两次的节点，在第二次出现的时候再打印
     * 中序结果：x x 4 2 5 1 x 6 3 7
     *
     * 本质是  只在  cur.left != null && mostRight.right == null 不打印即可
     * 这种节点会回到自己，在第一次遇到的时候不需要打印！！！
     *
     */
    public static void MorrisIn(TreeNode head){
        while(head != null){
            if (head.left == null){
                System.out.print(head.val + " ");
                head = head.right;
            } else {
                TreeNode mostRight = head.left;
                while (mostRight.right != null &&  mostRight.right != head){
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null){
                    System.out.print("x ");

                    mostRight.right = head;
                    head = head.left;
                }

                if (mostRight.right == head){
                    // 第二次来到的节点  观察Moris顺序遍历.jpg
                    mostRight.right = null;
                    System.out.print(head.val + " ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }




    /**
     * 后序遍历
     *
     * 注意观察Moris遍历的顺序
     * Moris遍历  每次在移动cur时打印cur节点，可知遍历的顺序为
     * Moris顺序为: 1 2 4 2 5 1 3 6 3 7
     *
     * 后序遍历
     * （核心）必须要回到自己两次且第二次回到自己的时候再在打印，只回到自己一次的节点不打印，最后逆序打印整棵树的右边界 ！！！
     * 对于没有左子树的节点，只会来到其节点一次 没有打印时机，
     * 只在第二次来到自己节点的时机打印节点，且不打印当节点  而是打印当前节点的左子树的右边界（一路right.right），最后打印整棵树的右边界
     * 如下
     * 第二次来到2节点时   逆序打印2的右边界  4
     * 第二次来到1节点时   逆序打印1的右边界  5 2
     * 第二次来到3节点时   逆序打印3的右边界  6
     * 最后逆序打印整棵树的右边界 7 3 1
     *
     */
    public static void MorrisPos(TreeNode head){
        TreeNode cur =head;

        while(cur !=null){
            if(cur.left == null){
                // 只能来到自己节点一次  不打印
                // System.out.print(cur.value + " ");
                cur = cur.right;
            }else{
                // cur.left != null
                TreeNode mostRight = cur.left;
                // 跳出循环有两种情况
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    // 进入这里表示  cur.left != null && mostRight.right == null
                    // 存在左子节点 且一定会再一次回到当前节点
                    // 对于cur.left == null的节点只能来到一次  所以遇到了直接打即可
                    // System.out.print(cur.value + " ");// 移动之前打印当前节点
                    cur = cur.left;// cur左移
                }
                if(mostRight.right == cur){
                    mostRight.right = null;
                    // 第二次回到自己节点时  逆序打印当前节点右边界  只在这个时机打印且是逆序的
                    printEdge(cur.left);// 逆序方式打印当前节点左子树的右边界  注意不打印当前节点
                    cur = cur.right;// cur右移
                }
            }
        }
        printEdge(head);
        System.out.println();
    }

    /**
     * 逆序方式打印整个链表
     * 先把整个链表逆序一遍，再逆序回来
     * 1->2->3->4->null
     */
    private static void printEdge(TreeNode node) {
        TreeNode pre = null;
        TreeNode next = null;
        while (node != null) {
            next = node.right;
            node.right = pre;
            pre = node;
            node = next;
        }
        TreeNode help = pre;
        pre = node;
        node = help;
        while (node != null) {
            System.out.print(node.val + " ");
            next = node.right;
            node.right = pre;
            pre = node;
            node = next;
        }
    }


    public static void main(String[] args) {
        TreeNode head = new TreeNode(1);
        head.left =new TreeNode(2);
        head.right = new TreeNode(3);
        head.left.left = new TreeNode(4);
        head.left.right = new TreeNode(5);
        head.right.left = new TreeNode(6);
        head.right.right = new TreeNode(7);

        Morris(head);
        MorrisPre(head);
        MorrisIn(head);
        MorrisPos(head);

    }



    /**
     * Moris遍历  最优解！！！
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal3(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        process(root, ans);
        return ans;
    }


    //**************************下面代码是Moris遍历相关的代码  end************************************












}
