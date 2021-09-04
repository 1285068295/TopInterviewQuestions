package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/4
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0002_AddTwoNumbers {
    // 不要提交这个类描述
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int value) {
            this.val = value;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode h1 = l1;
        ListNode h2 = l2;
        // 进位标志
        boolean ca;
        // 当前节点的值
        int curNum = 0;

        // 先计算出第一个节点来  没有进位
        curNum = h1.val + h2.val;
        ca = (curNum >= 10) ? true : false;
        curNum %= 10;
        ListNode firstNode = new ListNode(curNum);
        ListNode head = firstNode;

        h1 = h1.next;
        h2 = h2.next;

        while (h1 != null || h2 != null) {
            if (h1 != null && h2 != null) {
                // 当前节点的值
                curNum = ca ? (h1.val + h2.val + 1) : (h1.val + h2.val);
                ca = (curNum >= 10) ? true : false;
                curNum %= 10;

                head.next = new ListNode(curNum);
                head = head.next;
                h1 = h1.next;
                h2 = h2.next;
            } else {
                // 必然有一个为空
                ListNode h = h1 != null ? h1 : h2;
                curNum = ca ? (h.val + 1) : h.val;
                ca = (curNum >= 10) ? true : false;
                curNum %= 10;

                head.next = new ListNode(curNum);
                head = head.next;
                h1 = h1 != null ? h1.next : null;
                h2 = h2 != null ? h2.next : null;
            }

        }
        if (ca) {
            head.next = new ListNode(1);
        }
        return firstNode;
    }

}
