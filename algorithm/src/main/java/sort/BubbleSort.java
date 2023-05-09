package sort;

import java.util.Arrays;

/**
 * 冒泡算法：
 *      时间复杂度：O(n^2)  空间占用高（比较交换频繁）  稳定的（不会改变原有的顺序）
 */
public class BubbleSort{
    public int[] sort(int[] sourceArray) {
        int[] arrTmp = Arrays.copyOf(sourceArray, sourceArray.length);

        for(int i=1;i < arrTmp.length;i++) {
            boolean flag = true;
            for(int j=0; j <arrTmp.length-i;j++){
                if(arrTmp[j] > arrTmp[j+1]) {
                    int tmp = arrTmp[j];
                    arrTmp[j] = arrTmp[j+1];
                    arrTmp[j+1] = tmp;
                    flag = false;
                }
            }
            if (flag){ //经过比较后相邻的元素都是左小右大的情况直接退出
                break;
            }
        }
        return arrTmp;
    }
}
