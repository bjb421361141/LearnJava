package hashcode;

import hashcode.inf.HashFunction;

/**
 * Hash函数利用了乘法的不相关性
 * 较为常用
 */
public class BernsteinHash extends HashFunction {
    int seed;

    public BernsteinHash(int seed) {
        this.seed = seed;
    }

    @Override
    public int hash(String value) {
        int hash = 0;
        int i;
        for (i = 0; i < value.length(); ++i) {
            hash = seed * hash + value.charAt(i);  //常用的乘数还有131, 1313
        }
        return hash;
        //return  FNVHash1(value)
    }

    /**
     * FNV算法
     *
     * @param key 需要进行哈希值计算的内容
     * @return
     */
    public static int FNVHash1(String key) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }

    public static void main(String[] args) {
        BernsteinHash bernsteinHash = new BernsteinHash(31);
        bernsteinHash.hash("100");
    }
}
