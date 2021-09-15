package topinterviewquestions;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author ：Lisp
 * @date： 2021/9/16
 * @version: V1.0
 * @slogan:
 * @description :合并K个升序链表
 * 【解题思路】 使用小根堆处理
 */
public class Problem_0023_MergeKSortedLists {

    public static class ListNode {
        public int val;
        public ListNode next;
    }


    /**
     * k == lists.length
     * 0 <= k <= 10^4
     * 0 <= lists[i].length <= 500
     * -10^4 <= lists[i][j] <= 10^4
     * lists[i] 按 升序 排列
     * lists[i].length 的总和不超过 10^4
     *
     * @param lists
     * @return
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        Queue<ListNode> heap = new PriorityQueue<>((n1, n2) -> {
            return n1.val < n2.val ? -1 : 1;
        });

        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                heap.add(lists[i]);
            }
        }
        if (heap.isEmpty()) {
            return null;
        }



        ListNode head = heap.poll();
        ListNode pre = head;
        if (pre.next != null) {
            heap.add(pre.next);
        }



        while (!heap.isEmpty()) {
            ListNode cur = heap.poll();
            pre.next = cur;
            pre = cur;
            if (cur.next != null) {
                heap.add(cur.next);
            }
        }
        return head;
    }


    public static void main(String[] args) {

        ListNode n1 = new ListNode();
        n1.val = 1;


        ListNode n2 = new ListNode();
        n2.val = 1;


        ListNode[] lists = new ListNode[2];
        lists[0] = n1;
        lists[1] = n1;
        mergeKLists(lists);
    }
}