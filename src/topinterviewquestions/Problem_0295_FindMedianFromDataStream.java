package topinterviewquestions;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author ：Lisp
 * @date： 2021/10/15
 * @version: V1.0
 * @slogan:
 * @description : 提交时需要把 类名改为Problem_0295_FindMedianFromDataStream 替换为MedianFinder
 *
 * 数据流的中位数  堆结构使用经典题目！！！  必会！！！
 *
 * 【解题思路】
 * 使用两个堆结构，大根堆和小根堆，
 * 我们用两个优先队列 queMax 和 queMin 分别记录大于中位数的数和小于等于中位数的数。当累计添加的数的数量为奇数时，
 * queMin 中的数的数量比 queMax 多一个，此时中位数为 queMin 的队头。
 * 当累计添加的数的数量为偶数时，两个优先队列中的数的数量相同，此时中位数为它们的队头的平均值。
 *
 * 当我们尝试添加一个数 num 到数据结构中，我们需要分情况讨论：
 *  num ≤max{queMin}  此时 num 小于等于中位数，我们需要将该数添加到 queMin 中。新的中位数将小于等于原来的中位数，
 *                    因此我们可能需要将 queMin 中最大的数移动到 queMax 中。
 *
 *  num >max{queMin} 此时 num 大于中位数，我们需要将该数添加到 queMin 中。新的中位数将大于等于原来的中位数，
 *                   因此我们可能需要将 queMax 中最小的数移动到 queMin 中。
 *
 * 特别地，当累计添加的数的数量为 0 时，我们将 num 添加到 queMin 中
 *
 */
public class Problem_0295_FindMedianFromDataStream {


    /**
     * 大根堆比较器
     */
    public class MaxHeapComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;

//    public Problem_0295_FindMedianFromDataStrea() {
//        maxHeap = new PriorityQueue<>((a, b) -> {
//            return b - a;
//        });
//        minHeap = new PriorityQueue<>();
//    }

    /**
     * 虽然实现了但是代码效率还是低！！！ 第一版
     *
     * 效率极低！！！
     */
    public void addNum2(int num) {
        // 调整两个堆的数量始终 差值始终不超2
        // 如果两个堆的数量差超过2个 将元素多的堆 堆顶元素移入少的堆中

        // 下加入大根堆  再把大根堆 堆顶元素转移到小根堆  平衡一下

        // 仔细体会这段代码 妙哉呀！！！
        maxHeap.add(num);
        minHeap.add(maxHeap.poll());
        // 如果两个堆合起来的元素个数是奇数，小顶堆要拿出堆顶元素给大顶堆
        if (minHeap.size() - maxHeap.size() == 2) {
            maxHeap.add(minHeap.poll());
        }
    }

    public double findMedian2() {
        // 总数为偶数
        if (maxHeap.size() == minHeap.size()) {
            return ( maxHeap.peek() +   minHeap.peek()) / 2.0;
        }

        // 总数为奇数 一定是大根堆的数量多于小根堆的数量
        return minHeap.peek();
    }





    /** 下面是官方题解 */
    PriorityQueue<Integer> queMin;
    PriorityQueue<Integer> queMax;

    public Problem_0295_FindMedianFromDataStream() {
        queMin = new PriorityQueue<Integer>((a, b) -> (b - a));
        queMax = new PriorityQueue<Integer>((a, b) -> (a - b));
    }

    public void addNum(int num) {
        // 1 2 3 4
        // 有点意思呀！！！ 大小根堆  交替的加入数据！！！
        if (queMin.isEmpty() || num <= queMin.peek()) {
            queMin.offer(num);
            // 数量差为2个
            if (queMax.size() + 1 < queMin.size()) {
                queMax.offer(queMin.poll());
            }
        } else {
            queMax.offer(num);
            if (queMax.size() > queMin.size()) {
                queMin.offer(queMax.poll());
            }
        }
    }

    public double findMedian() {
        if (queMin.size() > queMax.size()) {
            return queMin.peek();
        }
        return (queMin.peek() + queMax.peek()) / 2.0;
    }





}
