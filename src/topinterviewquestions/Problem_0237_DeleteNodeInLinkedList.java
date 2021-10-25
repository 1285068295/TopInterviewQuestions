package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/19
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0237_DeleteNodeInLinkedList {
    public static class ListNode {
        int val;
        ListNode next;
    }


    public void deleteNode(ListNode node) {
        // 2 -> 3 -> 4 -> 5
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
