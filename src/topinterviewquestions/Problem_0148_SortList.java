package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/7
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 在归并排序的基础上排序  为实现空间法度为O(1) 不能使用递归 要用迭代实现
 * 方案一
 *  初始时len=1 len=2 ...
 *  左半部分为1个元素  右半部分为1个元素  进行merge
 *  左半部分为2个元素  右半部分为2个元素  进行merge
 *  左半部分为4个元素  右半部分为4个元素  进行merge
 *  左半部分为8个元素  右半部分为8个元素  进行merge
 *  ...
 *
 *  方案二
 *  利用快慢指针确定左右数组的边界
 *
 */
public class Problem_0148_SortList {

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

    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 链表的总长度
        int N = 0;
        ListNode cur = head;
        while (cur != null){
            N++;
            cur = cur.next;
        }

        int len = 1;
        int L = 0;
        int R = L + len;
        while (len < N){
            ListNode teamL = head;
            ListNode teamR = head;
            for (int i = 0; i < len; i++) {
                teamR = head.next;
            }
            // teamL ~ teamR 区间段进行合并
            for (int i = 0; i < N; ) {
                ListNode[] arr = merger(null, teamL, teamR);
                teamL = arr[0];
                teamR = arr[1];
                i += len;
            }
            // 合并的区间段长度扩大一倍
            len *= 2;
        }







        return head;

    }

    /**
     * TODO 这里合并代码没写完！！！
     *
     * 合并链表 至少要有两个节点
     * 从teamFirst位置开始合并链表
     *
     * @param preL 合并区间段的teamL的前一个元素
     */
    private static ListNode[] merger(ListNode preL, ListNode teamL, ListNode teamR) {

        // 只有一个点
        if (teamL == teamR) {
            return new ListNode[]{teamL, teamR};
        }

        // 1 -> 4 -> 3 -> null
        // 1 -> 2 -> 5 -> 6 -> 3 -> null
        // 1 -> 2 -> 7 -> 9 -> 5 -> 6 -> 8 -> null
        ListNode L = teamL, R = teamR;
        // R != null 最后一组可能没有len个元素
        while (R != null && L != teamR) {
            if (L.val <= R.val) {
                // 只移动 L
                preL = L;
                L = L.next;
            } else {
                //  TODO 待完善

                // 参见图片 Problem_0148_SortList.jpg
                // 这个想法很妙呀！！！
                // 把R放到L的前面  R指向R的下一个
                ListNode rnext = R.next;

                if (preL != null){
                    preL.next = R;
                }else {
                    // 第一节点就需要移动节点
                    L.next = rnext;
                }
                R.next = L;
                preL = R;
                R = rnext;
            }
        }
        return new ListNode[]{preL, L, R};
    }

    public static void main(String[] args) {
        ListNode head1 = new ListNode(5);
        ListNode head2 = new ListNode(2);
        ListNode head3  = new ListNode(4);
        ListNode head4  =new ListNode(3);
        head1.next = head2;
        head2.next = head3;
        head3.next = head4;

        merger(null, head1, head2);


    }

}
