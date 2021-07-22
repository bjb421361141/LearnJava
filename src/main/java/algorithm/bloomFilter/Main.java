package algorithm.bloomFilter;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String bloomFilterName = "MyBloomFilter"; //固定名称
            List<String> dataList = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                dataList.add(String.valueOf(i));
            }
            BloomFilterRedis  filterRedis = new BloomFilterRedis(bloomFilterName, dataList);
            boolean trueFlag = filterRedis.mightContain("MyBloomFilter","678");
            boolean falseFlag = filterRedis.mightContain("MyBloomFilter","12345678");
            System.out.println(trueFlag);
            System.out.println(falseFlag);
//            String str = "123456";
//            System.out.println(str.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
