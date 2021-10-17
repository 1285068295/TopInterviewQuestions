package topinterviewquestions;

import java.util.HashMap;

/**
 * @author ：Lisp
 * @date： 2021/10/7
 * @version: V1.0
 * @slogan:
 * @description :最近最少使用算法 LRU
 *
 * 【说明】
 *  不能使用系统提供的LinkedList这道题就是在考察你造结构的能力
 *   这道题很经典 一定要做到可以手写的程度。
 *
 *
 *  我们可以使用HashMap + 自己写的双向链表 （必须是双向链表，因为这样拿到链表的节点地址后 方便调整前后的节点的位置关系）
 *  总是把最新的操作的节点放到最后，get已有节点时 也要把已有的节点更新到最后位置  链表 从头到尾 操作时间点距离当前时间点越来越近
 *
 *
 *   在leetcode上提交时，把文字替换成下面的代码
 *   然后把类名、构造方法名从Problem_0146_LRUCache改为LRUCache即可
 *
 *
 *    整体思路简述：内部使用MyCache  MyCache内部使用map和双向链表
 *
 */
public class Problem_0146_LRUCache {

    /**
     * 内部使用MyCache类实现
     */
    private MyCache<Integer, Integer> cache;

    public Problem_0146_LRUCache(int capacity) {
        this.cache = new MyCache<>(capacity);
    }

    public int get(int key) {
        Integer ans = cache.get(key);
        return ans == null ? -1 : ans;

    }

    public void put(int key, int value) {
        cache.set(key, value);
    }




    /**
     * 设计成泛型的形式
     */
    public static class Node<K, V> {
        // 双向链表 前后指针
        public Node<K, V> pre;
        public Node<K, V> next;

        // 把key value封装成内部的node类
        public K key;
        public V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    /**
     * 用来存节点的双向链表
     */
    public static class NodeDoubleLinkedList<K, V>{
        public Node<K, V> head;
        public Node<K, V> tail;

        public void add(Node<K, V> newNode) {
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                // 踩坑点 双向链表指针处理
                tail.next = newNode;
                newNode.pre = tail;
                tail = newNode;
            }
        }

        /**
         * 把节点移动到最后的节点
         * 必须要真node是存在于链表上的
         * get方法调用完后使用这个方法
         *
         * coding 很难搞呀  各种空指针
         */
        public void moveNodeToTail(Node<K, V> node){
            if (node == tail) {
                return;
            }

            // 存在两个及两个以上的节点 且要移动的节点不是链表的最后位置
            Node pre = node.pre;
            Node next = node.next;

            node.next = null;
            node.pre = tail;

            if (node != head) {
                // 等价于 pre != null
                pre.next = next;
            } else {
                head = next;
            }
            next.pre = pre;
            tail.next = node;
            tail = node;
        }

        /**
         * 删除头节点 超过最大容量时删除最早的节点（头节点）
         */
        public void removeHead() {
            if (head == tail){
                head = null;
                tail = null;
                return;
            }
            Node next = head.next;
            head.next = null;
            head = next;
            head.pre = null;
        }


        // for test
        public static void testMoveNode(){
            NodeDoubleLinkedList<Integer, Integer> linkedList = new NodeDoubleLinkedList<>();

            Node<Integer, Integer> n1 = new Node<>(1,1);
            Node<Integer, Integer> n2 = new Node<>(2,2);
            Node<Integer, Integer> n3 = new Node<>(3,3);

            n1.next = n2;
            n2.next = n3;
            n2.pre = n1;
            n3.pre = n2;
            linkedList.add(n1);
            linkedList.add(n2);
            linkedList.add(n3);
            linkedList.moveNodeToTail(n1);
        }

        // test add over capacity
        public static void testAdd(){
            Problem_0146_LRUCache LURCache = new Problem_0146_LRUCache(3);
            LURCache.put(1,1);
            LURCache.put(2,2);
            LURCache.put(3,3);
            LURCache.put(4,4);
            LURCache.get(4);
            LURCache.get(3);
            LURCache.get(2);
            LURCache.get(1);
            LURCache.put(5,5);
            LURCache.get(1);
            LURCache.get(2);
            LURCache.get(3);
            LURCache.get(4);
            LURCache.get(5);
        }


        // for test
        public static void main(String[] args) {
//            testMoveNode();
            testAdd();
        }

    }


    /**
     * 内部类
     * @param <K>
     * @param <V>
     */
    public static class MyCache<K, V> {
        private HashMap<K, Node<K, V>> keyNodeMap;
        private NodeDoubleLinkedList<K, V> nodeList;
        private final int capacity;

        public MyCache(int capacity) {
            if (capacity < 0) {
                throw new RuntimeException("capacity must more than 0.");
            }
            this.capacity = capacity;
            keyNodeMap = new HashMap<>();
            nodeList = new NodeDoubleLinkedList<>();
        }


        public V get(V key) {
            Node<K, V> node = keyNodeMap.get(key);
            if (node == null) {
                return null;
            }
            nodeList.moveNodeToTail(node);
            return node.value;
        }

        /**
         * 不存在就添加一个新节点 否则更新节点
         * @param key
         * @param value
         */
        public void set(K key, V value) {
            if (keyNodeMap.containsKey(key)) {
                Node<K, V> node = keyNodeMap.get(key);
                // 更新value
                node.value = value;
                nodeList.moveNodeToTail(node);
            } else {
                Node<K, V> newNode = new Node<K, V>(key, value);
                keyNodeMap.put(key, newNode);
                nodeList.add(newNode);
                if (keyNodeMap.size()> capacity){
                    Node head = nodeList.head;
                    nodeList.removeHead();
                    // 踩坑点map中没有删除 踩坑二 删除的是head.key 不是head.value
                    keyNodeMap.remove(head.key);
                }
            }
        }
    }
}
