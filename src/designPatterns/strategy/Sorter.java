package designPatterns.strategy;

import designPatterns.strategy.inf.IComparable;

/**
 * 根据不同策略进行排序 ：使用泛型可以将类别拓宽
 * 所有策略实现都需实现策略接口方法，便于在工具类中进行调用
 * @param <T>
 */
public class Sorter<T> {

    /**
     * 将对象的比较方法抽离出对象，符合开闭原则
     * @param toSortLst
     * @param comparator
     */
    public void sort(T[] toSortLst, IComparable<T> comparator) {
        for (int i = 0; i < toSortLst.length - 1; i++) {
            int minPos = i;
            for (int j = i + 1; j < toSortLst.length; j++) {
                minPos = comparator.compare(toSortLst[j], toSortLst[minPos]) == -1 ? j : minPos;
            }
            swap(toSortLst, i, minPos);
        }
    }

    void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
