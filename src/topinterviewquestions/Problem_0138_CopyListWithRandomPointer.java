package topinterviewquestions;

import java.util.HashMap;

/**
 * @author ：Lisp
 * @date： 2021/10/6
 * @version: V1.0
 * @slogan:
 * @description :深度复制链表
 * 【解题思路】
 *  方案一  使用map key-旧的的节点  value-新的节点
 *  方案二  把新的节点串到旧的的节点后面，利用结构分放置关系代替了map的一一对应关系
 *         a->b->c->null   a->a'->b->b'->c->c'->null
 */
public class Problem_0138_CopyListWithRandomPointer {


    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList1(Node head) {
        // key-旧的节点 value-新的节点
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while(cur != null){
            map.put(head, new Node(cur.val));
            cur = cur.next;
        }


        // 构建新的链表  next random
        cur = head;
        Node newHead = map.get(cur);
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }



    public static Node copyRandomList2(Node head) {
        if (head == null){
            return null;
        }

        Node cur = head;
        while(cur != null){
            // 先构建新节点
            //  a->b->c->null   a->a'->b->b'->c->c'->null
            Node curNext = new Node(cur.val);
            Node tmp = cur.next;
            cur.next = curNext;
            curNext.next = tmp;
            cur = tmp;
        }

        // 修正新的节点的 random
        cur = head;
        Node newNode = cur.next;
        while (cur != null) {
            newNode.random = cur.random == null ? null : cur.random.next;
            // 踩坑点 空指针满天飞呀
            cur = cur.next.next;
            newNode = cur == null ? null : cur.next;
        }

        // 新旧链表拆离
        cur = head;
        Node newHead = cur.next;
        newNode = newHead;
        while (cur != null) {
            cur.next = newNode.next;
            cur = cur.next;
            // 踩坑点 空指针判断
            if(cur != null ){
                newNode.next = cur.next;
                newNode = newNode.next;
            }
        }
        return newHead;
    }


    public static void main(String[] args) {
        // [[7,null],[13,0],[11,4],[10,2],[1,0]]
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);

        n1.next = n2;
        n2.next = n3;
        n1.random = n3;
        n2.random = n1;

        Node n = copyRandomList2(n1);

    }

}
