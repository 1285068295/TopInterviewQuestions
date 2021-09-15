package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/15
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0021_MergeTwoSortedLists {


    public static class ListNode {
        public int val;
        public ListNode next;
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        // 找到头节点
        ListNode newHead = null;
        if (l1.val < l2.val) {
            newHead = l1;
            l1 = l1.next;
        } else {
            newHead = l2;
            l2 = l2.next;
        }
        ListNode node = newHead;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                node.next = l1;
                node = node.next;
                l1 = l1.next;
            } else {
                node.next = l2;
                node = node.next;
                l2 = l2.next;
            }
        }

        node.next = l1 != null ? l1 : l2;
        return newHead;
    }


    /**
     * teacher
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode head = l1.val <= l2.val ? l1 : l2;
        ListNode cur1 = head.next;
        ListNode cur2 = head == l1 ? l2 : l1;
        ListNode pre = head;
        while (cur1 != null && cur2 != null) {
            if (cur1.val <= cur2.val) {
                pre.next = cur1;
                cur1 = cur1.next;
            } else {
                pre.next = cur2;
                cur2 = cur2.next;
            }
            pre = pre.next;
        }
        pre.next = cur1 != null ? cur1 : cur2;
        return head;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode();
        l1.next = new ListNode();
        l1.next.next = new ListNode();
        l1.val = 1;
        l1.next.val = 2;
        l1.next.next.val = 4;



        ListNode l2 = new ListNode();
        l2.next = new ListNode();
        l2.next.next = new ListNode();

        l2.val = 1;
        l2.next.val = 3;
        l2.next.next.val = 4;

        ListNode head = mergeTwoLists(l1, l2);
    }

}
