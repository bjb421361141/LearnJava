package algorithm.hashcode;

import algorithm.hashcode.inf.HashFunction;

/**
 * 位运算哈希函数
 * <p>
 * &	与	两个位都为1时，结果才为1
 * |	或	两个位都为0时，结果才为0
 * ^	异或	两个位相同为0，相异为1
 * ~	取反	0变1，1变0
 * <<	左移	各二进位全部左移若干位，高位丢弃，低位补0
 * >>	右移	各二进位全部右移若干位，对无符号数，高位补0，有符号数，各编译器处理方法不一样，有的补符号位（算术右移），有的补0（逻辑右移）
 */
public class RotatingHash extends HashFunction {
    private int prime;

    public RotatingHash(int prime) {
        this.prime = prime;
    }

    @Override
    public int hash(String value) {
        int hash, i;
        for (hash = value.length(), i = 0; i < value.length(); ++i)
            hash = (hash << 4) ^ (hash >> 28) ^ value.charAt(i);
        return (hash % prime);
    }

    public static void main(String[] args) {
        //二进制位移 左移右移
        System.out.println(1 << 4);   //相当于num 乘以 2^4
        System.out.println(16 >> 4);  //相当于num 除以 2^4
        System.out.println(-16 >> 4); // 符号位不进入位移
        System.out.println(-16 >>> 4); // 无符号位移

        //位运算  异或  或  与
        System.out.println(1 ^ 4); // 001 和 100  --> 101  值 5
        System.out.println(1 | 4); // 001 和 100 --> 101  值 5
        System.out.println(1 & 4);// 001 和 100 --> 000 值 0

//        int seed = 3;  //11
//        String value = "abcdefg"; //ascii码 97~ 103
//        for (int i = 0; i < value.length(); i++) {
//            System.out.println(seed ^ value.charAt(i));
//        }

        //位运算  取反
//        原码  反码（符号位不变真值区取反）  补码（符号位不变取反+1）
//        计算机中的数都是根据补码进行保存计算的 ，正数的3码是一致的 负数如果不知道是哪一个码则无法推出真值
//        时钟回拨例子：8点 拨回 6点 -2(真值) 或 +10(补值)可得。 计算机中的减法计算也是类似的 使用补码来进行处理。
        System.out.println(Integer.toBinaryString(-1));  //原码为：1***1 反码为 1###00 补码为 1###11
        System.out.println(Integer.toBinaryString(3));  //原码为：0***11 反码为 0***11 补码为 0***11
        System.out.println(Integer.toBinaryString(2));  //原码为：0***10 反码为 0***10 补码为 0***10
        System.out.println(~3); // 3的补码为 0***11  取反符号操作后的补码 1###00   输出: 补码减1后取反得 1***100 => -4

        System.out.println(Integer.toBinaryString(16 >>> 4));
    }
}
