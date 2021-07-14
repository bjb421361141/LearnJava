package algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSelectSort {
    public int[] sort(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        return quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 快速排序
     *
     * @param arr   需要进行分区的数组
     * @param left  开始下标
     * @param right 结束下标
     * @return 返回排好序的数组
     */
    private int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partitionTwoDirection(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
        return arr;
    }


    /**
     * 单方向进行替换
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 右边界
     * @return
     */
    private int partitionOneDirection(int[] arr, int left, int right) {
        // 设定基准值（pivot）
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, pivot, index - 1); //将基准数据放置 index-1 位置
        return index - 1;
    }

    private int partitionTwoDirection(int[] arr, int left, int right) {
        int pivot = left;
        int fidx = left + 1;
        int bidx = right;
        while (fidx < bidx) {
            while (fidx < bidx && arr[fidx] < arr[pivot]) {
                fidx++;
            }
            while (fidx < bidx && arr[bidx] > arr[pivot]) {
                bidx--;
            }
            if (fidx < bidx) {
                swap(arr, fidx, bidx);
            }
            if(fidx == bidx){
                swap(arr, fidx, bidx+1);
            }
        }
        swap(arr, fidx - 1, pivot);
        return fidx;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 9, 8};
        QuickSelectSort sort = new QuickSelectSort();
        int[] arrTmp = sort.sort(arr);
        for (int o : arrTmp) {
            System.out.print(o + ",");
        }
    }
}
