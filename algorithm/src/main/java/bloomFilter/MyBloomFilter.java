package bloomFilter;

import hashcode.BernsteinHash;
import hashcode.inf.HashFunction;

import java.util.BitSet;

/**
 * 布隆算法第一个版本
 * fixme 容错率 和  数据散列情况
 * <p>
 * 布隆算法常用于大数据的筛查
 * 也用于缓存穿透的校验来缓解数据库压力
 */
public class MyBloomFilter {
    /**
     * 一个长度为10 亿的比特位  容量大的情况下误差率小
     */
    private static final int DEFAULT_SIZE = 256 << 22;   // 2^8 * 2^22
//    private static final int DEFAULT_SIZE = 256 << 4;   // 2^12

    /**
     * 为了降低错误率，使用加法hash算法，所以定义一个8个元素的质数数组
     * 加法哈希函数
     */
    private static final int[] seeds = {3, 5, 7, 9, 31, 33};

    /**
     * 相当于构建 8 个不同的hash算法
     */
    private static HashFunction[] functions = new HashFunction[seeds.length];

    /**
     * 初始化布隆过滤器的 bitmap
     */
    private static BitSet bitset = new BitSet(DEFAULT_SIZE);

    MyBloomFilter() {
        //初始化哈希函数
        for (int i = 0; i < functions.length; i++) {
            functions[i] = new BernsteinHash(seeds[i]);
        }
    }

    /**
     * 添加哈希值映射
     *
     * @param value 值
     */
    public void add(String value) {
        if (value != null) {
            for (HashFunction f : functions) {
                bitset.set(f.hash(value) % DEFAULT_SIZE, true);
            }
        }
    }

    /**
     * 验证哈希值映射
     * 一个 hash 函数返回 false 则跳出循环
     *
     * @param value 值
     * @return
     */
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (HashFunction f : functions) {
            ret = bitset.get(f.hash(value) % DEFAULT_SIZE);
            if (!ret) {
                break;
            }
        }
        return ret;
    }

    /**
     * 测试。。。
     */
    public static void main(String[] args) {
        MyBloomFilter myBloomFilter = new MyBloomFilter();
        for (int i = 0; i < 10000; i++) {
            myBloomFilter.add(String.valueOf(i));
        }
        String id = "234";
        myBloomFilter.add(id);

        System.out.println(myBloomFilter.contains(id));   // true
        System.out.println(myBloomFilter.contains("2345"));  //false
    }
}