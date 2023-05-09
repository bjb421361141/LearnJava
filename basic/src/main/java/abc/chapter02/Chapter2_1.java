package abc.chapter02;

/**
 * java
 * 基础数据类型：
 * 1、数值型
 * 整型：byte（1字节） 、short(2字节)、int（4字节）、long(8字节)
 * 浮点型：float(4字节)、double（8字节）
 * 2、字符类型：chart（1字节）
 * 3、布尔类型：boolean（字面量：true，false 实际存储空间为4字节）
 * 引用数据类型
 * 类对象，接口，数组，
 *
 * 运算规则包括：
 *      byte 、short 、char ---> int  --->  long  ---> float ---> double
 * 		① 自动类型提升 容量小转成容量大（容量大小指数据表示范围）
 * 		② 强制类型转换（有可能产生值溢出）
 * 	byte、short、char类型的变量之间做运算，结果为int类型。
 *
 * 	二进制的理解
 *    正数：原码、反码、补码三码合一。
 *    负数：原码、反码、补码不相同。了解三者之间的关系。
 *    计算机的底层是以`补码`的方式存储数据的。
 *
 *
 */
public class Chapter2_1 {
    public static void main(String[] args) {
        //整型数值类型默认为int类型，浮点型默认为double
        byte b = 127; //-128~127
        short s = 32767;    //-32768~32767 符号位+15位数值
        int i = 2100000000; //-2^31-1 ~2^31-1 约21亿
        long l = 1234567890123456789L; //-2^63-1 ~ -2^63-1
        float f = 12.3f; // -3.4E38(-3.4x10^38)~3.4E38(-3.4x10*38)
        double d = 12.3; // -1.7E308(-1.7x10^308)~1.7E308(-1.7x10*308)

        //byte、short
        byte b1 = 1;
        short s1 = 1;
        short s2 = 1;
        int sum0 = b1 + s2; //short s3 = b1 + s2 编译会报错
        int sum1 = s1 + s2; //short、byte相加默认返回的是int类型
        short sumVal =(short)(b1 + s1);
        long lf = (long) f; //long 类型8字节 float 4字节
        //有且仅有一个字符
        char c = 'a';
        char c1 = '\u0061'; //使用unicode来表示 十六进制
        char c2 = 97; //等于字符 a
        System.out.println(c+","+c1+","+c2);
        System.out.println('0'+'1'); //48、49
        boolean flag = true;

        int i1 = 3; //十进制
        i1 =0b11; //二进制
        i1 =03; //八进制
        i1 =0x3; //十六进制
        i1 = 'c'; //自动向上转型
        i1 +=1;
        System.out.println(i1);

        //float 计算精度不高 一般使用Decimal类来进行计算
        float ff1 = 123123123f;
        float ff2 = ff1 + 1;
        System.out.println(ff1);
        System.out.println(ff2);
        System.out.println(ff1 == ff2); //true

        //值溢出
        int i2 = 128;
        byte b2 = (byte) i2;
        System.out.println(b2); //-128

        //二进制
        int integer = -8; //正数3码合一
        //10000000000000000000000000001000 -8原码
        //11111111111111111111111111110111 -8反码
        //11111111111111111111111111111000 -8补码（反码+1）
        System.out.println(formatterBinaryStr(integer));
        System.out.println(Integer.toOctalString(integer));
        System.out.println(Integer.toHexString(integer));
        //二进制转10进制
        int rsl = Integer.valueOf("-1000",2);//s:符号位+原码
        int rsl0 = Integer.valueOf("-10",8);//s:符号位+原码
        int rsl1 = Integer.valueOf("-8",16);//s:符号位+原码
        System.out.println(rsl);
        System.out.println(rsl0);
        System.out.println(rsl1);

    }

    public static String formatterBinaryStr(int integer){
        String binaryStr = Integer.toBinaryString(integer);
        if (binaryStr.length() < 32) {
            String rslStr = "00000000000000000000000000000000";
            binaryStr = rslStr.substring(0,rslStr.length()-binaryStr.length()) + binaryStr;
        }
        return binaryStr;
    }
}
