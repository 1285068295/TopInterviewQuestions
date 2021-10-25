package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/19
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 判断一个链表是否为回文链表
 *
 * 这道题的反转链表操作很经典！！！
 *
 */
public class Problem_0234_PalindromeLinkedList {
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }


    public static boolean isPalindrome(ListNode head) {

        if (head.next == null) {
            return true;
        }


        ListNode node = head;
        // 统计节点总数
        int count = 0;
        while (node != null) {
            node = node.next;
            count++;
        }


        ListNode fast = head;
        ListNode slow = head;
        int num = count / 2;
        while (num-- != 0) {
            fast = fast.next;
        }

        // 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null
        // 从链表中中间 开始反转链表的后半部分链表
        ListNode rightHalfHead = reverse(fast);
        ListNode rightHead = rightHalfHead;


        boolean flag = true;
        while (rightHalfHead != null) {
            if (rightHalfHead.val != slow.val) {
                flag = false;
                break;
            }
            rightHalfHead = rightHalfHead.next;
            slow = slow.next;
        }

        // 恢复原来的链表
        ListNode tmp = head;
        // 为了找到tmp.next = null的位置
        num = count / 2;
        while (num-- > 0) {
            tmp = tmp.next;
        }

        tmp.next = reverse(rightHead);
        return flag;
    }

    /**
     *  4 -> 5 -> 6 -> null
     *
     *  null <- 4 <- 5 <- 6
     */
    private static ListNode reverse(ListNode node) {
        ListNode rightHalfHead = null;
        while(node != null){
            ListNode tmp = node.next;
            node.next = rightHalfHead;
            rightHalfHead = node;
            node = tmp;
        }
        return rightHalfHead;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
//        head.next.next = new ListNode(3);
//        head.next.next.next = new ListNode(3);
//        head.next.next.next.next = new ListNode(2);
//        head.next.next.next.next.next = new ListNode(1);

        System.out.println(isPalindrome(head));

    }


}
