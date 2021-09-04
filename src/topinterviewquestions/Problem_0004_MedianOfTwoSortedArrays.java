package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/4
 * @version: V1.0
 * @slogan:
 * @description :寻找两个正序数组的中位数
 *
 * 【思路】
 * 需要两个小函数
 * 1 getUpMedian函数可以计算 两个等长度数组排序后  上中位数
 * 2 findKthNum函数可以计算 两个不等长数组排序后  第K大的数
 *
 */
public class Problem_0004_MedianOfTwoSortedArrays {


    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size = nums1.length + nums2.length;
        boolean even = (size & 1) == 0;
        if (nums1.length != 0 && nums2.length != 0) {
            if (even) {
                // 分别求数组的中位数相加除以2得到两个数组的中位数
                return (double) (findKthNum(nums1, nums2, size / 2) + findKthNum(nums1, nums2, size / 2 + 1)) / 2D;
            } else {
                return findKthNum(nums1, nums2, size / 2 + 1);
            }
        } else if (nums1.length != 0) {
            if (even) {
                return (double) (nums1[(size - 1) / 2] + nums1[size / 2]) / 2;
            } else {
                return nums1[size / 2];
            }
        } else if (nums2.length != 0) {
            if (even) {
                return (double) (nums2[(size - 1) / 2] + nums2[size / 2]) / 2;
            } else {
                return nums2[size / 2];
            }
        } else {
            return 0;
        }
    }

    /**
     * 时间复杂度  O(log(短数组长度))
     * @param arr1
     * @param arr2
     * @param kth
     * @return
     */
    public static int findKthNum(int[] arr1, int[] arr2, int kth) {
        int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
        int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
        int l = longs.length;
        int s = shorts.length;
        // 踩坑点  =s 和 =l 的边界条件处理
        if (kth <= s) {
            // A 1  2  3  4  5  6  7  8   9  10                               -10个数
            // B 1' 2' 3' 4' 5' 6' 7' 8'  9' 10' 11' 12' 13' 14' 15' 16' 17' -17个数
            // kth = 5
            // A[1 2 3 4 5]  B[1 2 3 4 5] 求上中位数
            return getUpMedian2(shorts, 0, kth - 1, longs, 0, kth - 1);
        }else if (kth <= l) {
            // A 1  2  3  4  5  6  7  8  9  10                               -10个数
            // B 1' 2' 3' 4' 5' 6' 7' 8' 9' 10' 11' 12' 13' 14' 15' 16' 17' -17个数
            // kth = 15
            // B中可以排除  1' 2' 3' 4'     16'  17'
            // B中剩下  5' 6' 7' 8' 9' 10' 11' 12' 13' 14' 15' 共11个数
            // A中剩下  1  2  3  4  5  6   7   8   9   10      共10个数
            // 手动验证B中5'  B[5'] > A[10] 返回B[5']
            // A[1  2  3  4  5  6   7   8   9   10]  B[6' 7' 8' 9' 10' 11' 12' 13' 14' 15'] 求上中位数  第10小的数
            // 加上B中排除的1' 2' 3' 4' 5' 5个数 刚好15个数
            if (longs[kth - s - 1] >= shorts[s - 1]) {
                // 踩坑的地方 等于情况的漏掉
                return longs[kth - s - 1];
            }
            // 踩坑点  BL = kth - s + 1

            // 第2段
            return getUpMedian2(shorts, 0, s - 1, longs, kth - s, kth - 1);
        }else{
            // kth > l
            // A 1  2  3  4  5  6  7  8  9  10                               -10个数
            // B 1' 2' 3' 4' 5' 6' 7' 8' 9' 10' 11' 12' 13' 14' 15' 16' 17' -17个数
            // kth = 23
            // B中可以排除  1' 2' 3' 4' 5' 6' 7' 8' 9' 10' 11' 12' 因为12'最远排到第22小  剩下 13' 14' 15' 16' 17' 5个数
            // A中可以排除  1  2  3  4  5  因为5 > B[17'] 最远排到第22小  剩下 6  7  8  9  10
            // A中剩下  6   7   8    9   10
            // B中剩下  13' 14' 15' 16'  17'
            // A中排除了5个数  B中排除了12个数
            // 手动验证A中6  B中13' 这两个都不满足条件时
            // A中剩下  7   8    9   10
            // B中剩下  14' 15' 16'  17'
            // 排除的数  A中6个  + B中 13个 + 要求的第4小的数  共23个数
            if (shorts[kth - l - 1] >= longs[l - 1]) {
                // 手动排除A[6]
                return shorts[kth - l - 1];
            }
            if (longs[kth - s - 1] >= shorts[s - 1]) {
                // 手动排除B[13']
                return longs[kth - s - 1];
            }
            return getUpMedian2(shorts, kth - l, s - 1, longs, kth - s, l - 1);
        }
    }

    // teacher
    public static int findKthNum2(int[] arr1, int[] arr2, int kth) {
        int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
        int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
        int l = longs.length;
        int s = shorts.length;
        if (kth <= s) {
            return getUpMedian2(shorts, 0, kth - 1, longs, 0, kth - 1);
        } else if (kth <= l) {
            // 第2段
            if (longs[kth - s - 1] >= shorts[s - 1]) {
                return longs[kth - s - 1];
            }
            return getUpMedian2(shorts, 0, s - 1, longs, kth - s, kth - 1);
        } else {

            if (shorts[kth - l - 1] >= longs[l - 1]) {
                return shorts[kth - l - 1];
            }
            if (longs[kth - s - 1] >= shorts[s - 1]) {
                return longs[kth - s - 1];
            }
            return getUpMedian2(shorts, kth - l, s - 1, longs, kth - s, l - 1);
        }

    }
    /**
     * 迭代版本的实现  teacher
     */
    public static int getUpMedian1(int[] A, int s1, int e1, int[] B, int s2, int e2) {
        int mid1 = 0;
        int mid2 = 0;
        while (s1 < e1) {
            mid1 = (s1 + e1) / 2;
            mid2 = (s2 + e2) / 2;
            if (A[mid1] == B[mid2]) {
                return A[mid1];
            }
            // 奇数长度
            if (((e1 - s1 + 1) & 1) == 1) {
                if (A[mid1] > B[mid2]) {
                    if (B[mid2] >= A[mid1 - 1]) {
                        return B[mid2];
                    }
                    e1 = mid1 - 1;
                    s2 = mid2 + 1;
                } else { // A[mid1] < B[mid2]
                    if (A[mid1] >= B[mid2 - 1]) {
                        return A[mid1];
                    }
                    e2 = mid2 - 1;
                    s1 = mid1 + 1;
                }
            } else { // 偶数长度
                if (A[mid1] > B[mid2]) {
                    e1 = mid1;
                    s2 = mid2 + 1;
                } else {
                    e2 = mid2;
                    s1 = mid1 + 1;
                }
            }
        }
        return Math.min(A[s1], B[s2]);
    }



    /**
     * 计算两个等长度数组排序后  上中位数
     * 分两种情况  一种是数组的长度为偶数  一种是数组长度为奇数
     * 为了能够递归调用，需要传入A B数组的区间段
     *
     * 递归版本的实现
     * @param A
     * @param AL A数组的左区间位置
     * @param AR A数组的右区间位置
     * @param B
     * @param BL B数组的左区间位置
     * @param BR B数组的右区间位置
     * @return
     */
    public static int getUpMedian2(int[] A, int AL, int AR, int[] B, int BL, int BR ) {
        int midA = AL + ((AR - AL) >> 1);
        int midB = BL + ((BR - BL) >> 1);

        if (A[midA] == B[midB]) {
            return A[midA];
        }

        if (AL == AR) {
            return Math.min(A[midA], B[midB]);
        }

        // 踩坑点 长度判断需要加1处理
        if (((AR - AL + 1) & 1) == 0) {
            // 偶数
            if (A[midA] > B[midB]) {
                // A 1 2 3 4 5 6
                // B 1' 2' 3' 4' 5' 6'
                // 3 > 3' 时 A中排除 456 B中排除 1' 2' 3'
                // 注意 B的区间段为midB+1
                return getUpMedian2(A, AL, midA, B, midB + 1, BR);
            } else {
                return getUpMedian2(A, midA + 1, AR, B, BL, midB);
            }
        } else {
            // 奇数
            if (A[midA] > B[midB]) {
                // A 1 2 3 4 5
                // B 1' 2' 3' 4' 5'
                // 3 > 3' 时 A中排除 345 注意这里排除了3(排在第6位置) B中排除 1' 2'
                // 手动的验证下B中3' 满足条件直接返回 否则调用递归处理
                if (B[midB] > A[midA - 1]) {
                    return B[midB];
                }
                // 注意递归的区间段
                return getUpMedian2(A, AL, midA - 1, B, midB + 1, BR);
            } else {
                if (A[midA] > B[midB - 1]) {
                    return A[midA];
                }
                return getUpMedian2(A, midA + 1, AR, B, BL, midB - 1);
            }
        }
    }



}




