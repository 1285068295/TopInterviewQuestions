package topinterviewquestions;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author ：Lisp
 * @date： 2021/10/21
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0347_TopKFrequentElements {

    /**
     * 把数值 + 出现次数封装为Node
     */
    public static class Node {
        public int key;
        public int times;

        public Node(int key, int times) {
            this.key = key;
            this.times = times;
        }
    }

    /**
     * 按照词频从小到大排序
     */
    public static class MyComparator implements Comparator<Node> {

        @Override
        public int compare(Node n1, Node n2) {
            return n1.times - n2.times;
        }
    }


    public int[] topKFrequent(int[] nums, int k) {

        int N = nums.length;
        HashMap<Integer, Integer> times = new HashMap<>();

        // 统计每个数出现的次数
        for (int i = 0; i < N; i++) {
            times.put(nums[i], times.getOrDefault(nums[i], 0) + 1);
        }

        // 小根堆  围一个门槛，按照词频进行比较 堆顶的数据词频最小
        PriorityQueue<Node> heap = new PriorityQueue<>(k, new MyComparator());

        for (Map.Entry<Integer, Integer> entry : times.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());

            if (heap.size() < k) {
                heap.add(node);
            } else {
                if (heap.peek().times < node.times) {
                    heap.poll();
                    heap.add(node);
                }
            }
        }

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = heap.poll().key;
        }
        return ans;
    }


}
