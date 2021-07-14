package algorithm.sort;

import java.util.Arrays;

/**
 * 选择算法：
 *      时间复杂度 O(n^2)  空间复杂度低  不稳定算法（会改变未排序前元素的位置 例如 :[2,4,1,5]  --> [1,4,2,5] 改变了原有 2,4 的位置顺序）
 *      比较的多交换的少
 */
public class SelectionSort {
    public int[] sort(int[] sourceArray) {
        int[] arrTmp = Arrays.copyOf(sourceArray, sourceArray.length);

        for(int i=0;i <arrTmp.length ;i++) {
            int minPos = i;
            for(int j = i+1 ;j <arrTmp.length;j++) {
                if(arrTmp[minPos] > arrTmp[j]) {
                    minPos = j;
                }
            }
            if(i != minPos) { //进行位置互换
                int tmp = arrTmp[i];
                arrTmp[i] = arrTmp[minPos];
                arrTmp[minPos] = tmp;
            }
        }

        return arrTmp;
    }
}
