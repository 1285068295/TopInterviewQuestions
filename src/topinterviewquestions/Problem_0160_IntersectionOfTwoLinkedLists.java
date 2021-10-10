package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/9
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【思路】
 *  找出两个链表相差的节点个数  一个链表多走差值个节点  两指针同时走 一定会相遇
 *
 */
public class Problem_0160_IntersectionOfTwoLinkedLists {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null){
            return null;
        }


        ListNode h1 = headA;
        ListNode h2 = headB;

        // 统计节点个数
        int n1 = 0, n2 = 0;

        while (h1 != null) {
            n1++;
            h1 = h1.next;
        }

        while (h2 != null) {
            n2++;
            h2 = h2.next;
        }

        ListNode longListNode = n1 > n2 ? headA : headB;
        ListNode shorListNode = longListNode == headA ? headB : headA;

        int n = Math.abs(n1 - n2);
        while (n != 0) {
            longListNode = longListNode.next;
            n--;
        }

//        while (longListNode != shorListNode && longListNode != null) {
//            if (longListNode == shorListNode) {
//                return longListNode;
//            }
//            longListNode = longListNode.next;
//            shorListNode = shorListNode.next;
//        }
//
//        // 返回值要判断
//        return longListNode == shorListNode ? longListNode : null;


        // 老师写的代码更简洁呀！！！
        while (longListNode != shorListNode) {
            longListNode = longListNode.next;
            shorListNode = shorListNode.next;
        }
        return longListNode;

    }

    public static void main(String[] args) {

        ListNode h1 = new ListNode(4);
        h1.next = new ListNode(1);

        ListNode h2 = new ListNode(5);
        h2.next = new ListNode(6);
        h2.next.next = new ListNode(1);


        ListNode node = new ListNode(8);
        node.next = new ListNode(4);
        node.next.next = new ListNode(5);


        h1.next.next = node;
        h2.next.next.next = node;


        System.out.println(getIntersectionNode(h1, h2));




    }

}
