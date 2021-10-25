package followup;

/**
 * 拓展使用 308题
 * indexTree(树状数组) 可以改二维矩阵 三维矩阵  四维矩阵 ...
 * 而线段树使用与一维数组，代码改写二维矩阵 三维矩阵 很难写
 *
 * 前缀和数组  可以快速得到任意区间[i, j]上的累加和  sum[j]-sum[i-1]，但是只是用数组不会修改的情况
 *
 * 树状数组 indexTree 对于当数组单点修改时 依然可以快速的得到i~j 区间段上的累加和 logN 级别  修改时维护indexTree很快
 *
 * indexTree 再升级就是 线段树 比较重量级
 *
 * arr = {a b c d e f g h }
 * indexTree = {  0  废弃不用
 * 				  1  a
 * 				  2  a+b
 * 				  3  c
 * 				  4  a+b+c+d
 * 				  5  e
 * 				  6  e+f
 * 				  7  g
 * 				  8  a+b+c+d+e+f+g+h
 * 				}
 */
public class IndexTreeTest {

	public static class IndexTree {

		private int[] tree;
		private int N;

		public IndexTree(int size) {
			N = size;
			tree = new int[N + 1];
		}

		/**
		 * 计算 0 ~ index 上的累加和  每次抹去最右侧的1
		 *  求 0 ~ 13 的累加和为  index = 01101
		 *  					 index = 01100
		 *  					 index = 01000
		 *	这三个位置的indexTree的累加和相加
		 */
		public int sum(int index) {
			int ret = 0;
			while (index > 0) {
				ret += tree[index];
				// index & -index 表示提取出最右侧的 1
				index -= index & -index;
			}
			return ret;
		}

		/**
		 * index位置的数加上 d 同时更新indexTree
		 */
		public void add(int index, int d) {
			// 如 13 进制表示为  01101  当前的13位置需要加d
			// 下一个indexTree数组需要修改的位置为  13二进制下加上一个最右侧1 得到  01110位置需要更新
			// 再下一个需要更新的位置为  01110 + 一个最右侧的1 即加 00010  得到 10000 直到越界位置
			// 每一次要跟新的位置就是 当前的index在二进制下的值 加上其最右侧的1所代表的值
			while (index <= N) {
				tree[index] += d;
				index += index & -index;
			}
		}
	}

	public static class Right {
		private int[] nums;
		private int N;

		public Right(int size) {
			N = size + 1;
			nums = new int[N + 1];
		}

		public int sum(int index) {
			int ret = 0;
			for (int i = 1; i <= index; i++) {
				ret += nums[i];
			}
			return ret;
		}

		public void add(int index, int d) {
			nums[index] += d;
		}

	}

	public static void main(String[] args) {

		int[] a = {1,2,3,4,5,6,7,8,9};
		IndexTree tree1 = new IndexTree(8);
		tree1.sum(8);


		int N = 100;
		int V = 100;
		int testTime = 2000000;
		IndexTree tree = new IndexTree(N);
		Right test = new Right(N);
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int index = (int) (Math.random() * N) + 1;
			if (Math.random() <= 0.5) {
				int add = (int) (Math.random() * V);
				tree.add(index, add);
				test.add(index, add);
			} else {
				if (tree.sum(index) != test.sum(index)) {
					System.out.println("Oops!");
				}
			}
		}
		System.out.println("test finish");
	}

}
