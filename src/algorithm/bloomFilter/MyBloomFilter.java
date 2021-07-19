package algorithm.bloomFilter;

import algorithm.hashcode.BernsteinHash;
import algorithm.hashcode.inf.HashFunction;

import java.util.BitSet;

/**
 * 布隆算法第一个版本
 * <p>
 * 布隆算法常用于大数据的筛查
 * 也用于缓存穿透的校验来缓解数据库压力
 */
public class MyBloomFilter {
    /**
     * 一个长度为10 亿的比特位 fixme 貌似不用这么
     */
//    private static final int DEFAULT_SIZE = 256 << 22;   // 2^8 * 2^22
    private static final int DEFAULT_SIZE = 256 << 4;   // 2^12

    /**
     * 为了降低错误率，使用加法hash算法，所以定义一个8个元素的质数数组
     */
    private static final int[] seeds = {31, 33, 131, 1313, 13131};

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
     * @param value 值
     */
    public static void add(String value) {
        if (value != null) {
            for (HashFunction f : functions) {
                bitset.set(f.hash(value) % DEFAULT_SIZE, true);
            }
        }
    }

    /**
     * 验证哈希值映射
     *  一个 hash 函数返回 false 则跳出循环
     * @param value 值
     * @return
     */
    public static boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (HashFunction f : functions) {
            ret = bitset.get(f.hash(value)% DEFAULT_SIZE);
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




//        for (int i = 0; i < seeds.length; i++) {
//            functions[i] = new HashFunction(DEFAULT_SIZE, seeds[i]);
//        }
//
//        // 添加1亿数据
//        for (int i = 0; i < 100000000; i++) {
//            add(String.valueOf(i));
//        }
//        String id = "123456789";
//        add(id);
//
//        System.out.println(contains(id));   // true
//        System.out.println("" + contains("234567890"));  //false
    }
}