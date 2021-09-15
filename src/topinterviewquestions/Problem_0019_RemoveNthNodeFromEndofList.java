package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/15
 * @version: V1.0
 * @slogan:
 * @description :删除链表的倒数第N个节点
 * 【解题思路】(巧妙的利用了虚拟节点) 用一个指针先走 N 步 第二个指针再开始走  第一个指针到达链表末尾时，第二个指针指向的是要删除的链表节点的前一个节点
 *
 * 注意：快慢指针是指 两个指针的行走速度不一样  一个走两步  一个走一步  这道题不能算是快慢指针。
 *
 *【coding细节】：注意删除的是头节点的情况。
 *
 *
 */
public class Problem_0019_RemoveNthNodeFromEndofList {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    /**
     * 有可能删除的是头节点 此时头节点信息改变 所以方法需要返回头节点信息
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {

        if(head == null){
            return null;
        }
        // 构建一个虚拟节点来使用
        ListNode preHead = new ListNode(0);
        preHead.next = head;


        ListNode cur = head;
        // 第一节点先走 n 步
        while (n > 0 && cur != null) {
            cur = cur.next;
            n--;
        }
        if (n > 0) {
            // head=null   n大于的链表的长度情况
            return preHead.next;
        }

        // 代码优化 哈哈哈！！！
//        if (cur == null){
//            // 要删除的节点就是头节点的情况
//            return  preHead.next.next;
//        }

        // 一般情况的删除
        ListNode preNode = preHead;
        while(cur != null){
            cur = cur.next;
            preHead = preHead.next;
        }
        // 删除需要删除的节点
        preHead.next = preHead.next.next;
        return preNode.next;
    }


    /**
     * teacher
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            n--;
            if (n == -1) {
                pre = head;
            }
            if (n < -1) {
                pre = pre.next;
            }
            cur = cur.next;
        }
        if (n > 0) {
            return head;
        }
        if (pre == null) {
            return head.next;
        }
        pre.next = pre.next.next;
        return head;
    }



    public static void main(String[] args) {

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);

        ListNode n = removeNthFromEnd(null, 5);

    }

}
