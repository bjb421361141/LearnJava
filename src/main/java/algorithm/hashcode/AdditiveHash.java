package algorithm.hashcode;

import algorithm.hashcode.inf.HashFunction;

/**
 * 加法Hash就是把输入元素一个一个的加起来构成最后的结果
 * <p>
 * 结果的值域为[0,prime-1]
 */
public class AdditiveHash extends HashFunction {
    private int prime;

    public AdditiveHash(int prime) {
        this.prime = prime;
    }

    @Override
    public int hash(String value) {
        int hash, i;
        for (hash = value.length(), i = 0; i < value.length(); i++)
            hash += value.charAt(i);
        return (hash % prime);
    }

    public static void main(String[] args) {
        AdditiveHash a = new AdditiveHash(6);
        System.out.println(a.hash("str"));
    }
}
