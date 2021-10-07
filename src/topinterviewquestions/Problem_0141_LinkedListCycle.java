package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/7
 * @version: V1.0
 * @slogan:
 * @description :
 * 【链表判断是否存在环的机制】
 * 使用快慢指针 相遇时 快指针回到头部 每次移动一步 再次与慢指针相遇时就是环的人口
 */
public class Problem_0141_LinkedListCycle {


    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head.next.next;
        ListNode slow = head.next;

        while (fast != null && fast != slow) {
            fast = fast.next == null ? null:fast.next.next;
            slow = slow.next;
        }
        return fast != null;
    }



    /**
     * 存在环就返回环的入口节点
     */
    public static ListNode hasCycleWithFisrtNode(ListNode head) {
        if (head.next == null) {
            return null;
        }
        ListNode fast = head.next.next;
        ListNode slow = head.next;

        while (fast != null && fast != slow) {
            fast = fast.next == null ? null : fast.next.next;
            slow = slow.next;
        }

        if (fast == null) {
            return null;
        }

        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }


    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        System.out.println(hasCycle(head));
    }

}
