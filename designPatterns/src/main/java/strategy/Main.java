package strategy;

import java.util.Arrays;

/**
 * 策略模式是对象用来处理不同情况下的实现方式
 *
 * 应用场景：
 *      实现一个排序工具可以根据不同策略进行排序
 */
public class Main {
    public static void main(String[] args) {

        Cat[] catLst = {new Cat("Cat1",1, 3), new Cat("Cat2",2, 2), new Cat("Cat3",3, 1)};
        Sorter<Cat> sorter = new Sorter<>();
        sorter.sort(catLst,new CatWeightComparator());
        System.out.println(Arrays.toString(catLst));
        sorter.sort(catLst,new CatHeightComparator());
        System.out.println(Arrays.toString(catLst));
    }
}
