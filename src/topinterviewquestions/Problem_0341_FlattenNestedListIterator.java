package topinterviewquestions;

import java.util.*;

/**
 * @author ：Lisp
 * @date： 2021/10/22
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【解题思路一】
 *  直接递归深度优先 把参数的nestedList 转换成单纯的ArrayList<Integer>
 *  hasNext 调用ArrayList.iterator.hasNext  next调用 iterator.next
 *  思路一 使用了ArrayList的内部迭代器！！！
 *
 *  时间复杂度：初始化为 O(n)  next 和  hasNext 为  O(1)。其中 n 是嵌套的整型列表中的元素个数。
 *  空间复杂度：O(n)O(n)。需要一个数组存储嵌套的整型列表中的所有元素。
 *
 *【解题思路二】
 * 使用栈代替递归函数
 * 时间复杂度：初始化和next为 O(1)，hasNext 为均摊 O(1)。
 *
 * 空间复杂度：O(n)。最坏情况下嵌套的整型列表是一条链，我们需要一个 O(n)大小的栈来存储链上的所有元素。
 */
public class Problem_0341_FlattenNestedListIterator {

    /**
     * 不要提交这个接头类
     */
    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a
        // nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a
        // single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested
        // list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }


    public class NestedIterator implements Iterator<Integer> {

        // 利用递归直接将嵌套多层的 nestedList 转成单层的ArrayList
        private ArrayList<Integer> list;
        // ArrayList 内部迭代器
        private Iterator<Integer> iterator;

        public NestedIterator(List<NestedInteger> nestedList) {
            list = new ArrayList<>();
            process(nestedList, 0, list);
            iterator = list.iterator();
        }

        /**
         * 递归将多层的ArrayList转成单层的ArrayList
         *
         * [[1,1],2,[1,1]]
         */
        private void process(List<NestedInteger> nestedList, int index, ArrayList<Integer> list) {
            if (index != nestedList.size()) {
                NestedInteger cur = nestedList.get(index);
                if (cur.isInteger()) {
                    // 当前index位置为数字 直接加入list 递归处理下一个位置
                    list.add(cur.getInteger());
                } else {
                    // 当前位置为list 把当前位置看作list 从头部可是递归
                    process(nestedList.get(index).getList(), 0, list);
                }
                process(nestedList, index + 1, list);
            }
        }

        @Override
        public Integer next() {
            return iterator.next();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
    }


    /**
     * 使用栈来代替递归函数 为的是当[2, 3] 结束时 能够找到 [2, 3] 所在的位置  继续遍历后面的4
     *
     * 官方给的大答案 虽然使用stack 但是使用了 系统提供的 Iterator
     *
     *  我自己的想法是 记录 每个遍历的 List 的index 从而省去 Iterator  代码实现有难度 不好写！！！
     *
     * [[1, [2, 3] , 4 ], 5, [6, 7]]
     */
    public class NestedIterator2 implements Iterator<Integer> {

        // 存储列表的当前遍历位置
        private Deque<Iterator<NestedInteger>> stack;

        public NestedIterator2(List<NestedInteger> nestedList) {
            stack = new LinkedList<Iterator<NestedInteger>>();
            stack.push(nestedList.iterator());
        }

        @Override
        public Integer next() {
            // 由于保证调用 next 之前会调用 hasNext，直接返回栈顶列表的当前元素
            return stack.peek().next().getInteger();
        }

        @Override
        public boolean hasNext() {
            while (!stack.isEmpty()) {
                Iterator<NestedInteger> it = stack.peek();
                if (!it.hasNext()) { // 遍历到当前列表末尾，出栈
                    stack.pop();
                    continue;
                }
                // 若取出的元素是整数，则通过创建一个额外的列表将其重新放入栈中
                NestedInteger nest = it.next();
                if (nest.isInteger()) {
                    List<NestedInteger> list = new ArrayList<NestedInteger>();
                    list.add(nest);
                    stack.push(list.iterator());
                    return true;
                }
                stack.push(nest.getList().iterator());
            }
            return false;
        }
    }


    /**
     * 老师给的解答
     */
    public class NestedIterator3 implements Iterator<Integer> {

        private List<NestedInteger> list;
        private Stack<Integer> stack;
        private boolean used;

        public NestedIterator3(List<NestedInteger> nestedList) {
            list = nestedList;
            stack = new Stack<>();
            stack.push(-1);
            used = true;
            hasNext();
        }

        @Override
        public Integer next() {
            Integer ans = null;
            if (!used) {
                ans = get(list, stack);
                used = true;
                hasNext();
            }
            return ans;
        }

        @Override
        public boolean hasNext() {
            if (stack.isEmpty()) {
                return false;
            }
            if (!used) {
                return true;
            }
            if (findNext(list, stack)) {
                used = false;
            }
            return !used;
        }

        private Integer get(List<NestedInteger> nestedList, Stack<Integer> stack) {
            int index = stack.pop();
            Integer ans = null;
            if (!stack.isEmpty()) {
                ans = get(nestedList.get(index).getList(), stack);
            } else {
                ans = nestedList.get(index).getInteger();
            }
            stack.push(index);
            return ans;
        }

        private boolean findNext(List<NestedInteger> nestedList, Stack<Integer> stack) {
            int index = stack.pop();
            if (!stack.isEmpty() && findNext(nestedList.get(index).getList(), stack)) {
                stack.push(index);
                return true;
            }
            for (int i = index + 1; i < nestedList.size(); i++) {
                if (pickFirst(nestedList.get(i), i, stack)) {
                    return true;
                }
            }
            return false;
        }

        private boolean pickFirst(NestedInteger nested, int position, Stack<Integer> stack) {
            if (nested.isInteger()) {
                stack.add(position);
                return true;
            } else {
                List<NestedInteger> actualList = nested.getList();
                for (int i = 0; i < actualList.size(); i++) {
                    if (pickFirst(actualList.get(i), i, stack)) {
                        stack.add(position);
                        return true;
                    }
                }
            }
            return false;
        }

    }



    public static void main(String[] args) {




    }



}
