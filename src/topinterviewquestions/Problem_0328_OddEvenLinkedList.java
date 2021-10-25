package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/24
 * @version: V1.0
 * @slogan:
 * @description :奇偶链表
 * 想明白怎么串链表奇偶节点就行
 */
public class Problem_0328_OddEvenLinkedList {
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


    public static ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode oddHead = head;
        ListNode evenHead = head.next;
        ListNode mid = head.next;
        // 1 2 3 4 5 6
        while(oddHead.next != null && evenHead.next != null){
            oddHead.next = oddHead.next.next;
            oddHead = oddHead.next;
            evenHead.next = oddHead.next;
            evenHead = evenHead.next;
        }
        oddHead.next = mid;
        return head;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
//        head.next.next.next.next = new ListNode(5);

        oddEvenList(head);
    }
}
