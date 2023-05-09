package abc.chapter02;

/**
 * 算数运算符：+  -  +  -  *  /  %  (前)++  (后)++  (前)--  (后)--  +
 * 赋值运算符：=   +=、 -=、*=、 /=、%=
 * 比较运算符：==  !=  >   <   >=   <=  instanceof
 * 逻辑运算符：& &&  |  ||  ! ^
 * <p>
 * 运算符优先级：
 * 1 括号 ()、[]、{}
 * 2 正负号 +、-
 * 3 单元运算符 ++、--、~、！
 * 4 乘法、除法、求余 *、/、%
 * 5 加法、减法 +、-
 * 6 移位运算符 <<、>>、>>>
 * 7 关系运算符 <、<=、>=、>、instanceof
 * 8 等价运算符 ==、!=
 * 9 按位与 &
 * 10 按位异或 ^
 * 11 按位或 |
 * 12 条件与 &&
 * 13 条件或 ||
 * 14 三元运算符 ? :
 * 15 赋值运算符 =、+=、-=、*=、/=、%=
 */
public class Chapter2_2 {
    public static void main(String[] args) {

        int a0 = 1;
        int b0 = 2;
        double result = a0 / b0;//a/b = 0 再向上转成double类型
        System.out.println(result); //输出 0.0

        //自增、自减 需要注意的点
        int i = 1;
        int rsl = i++ + ++i * i++; //运算符优先级 自增、自减 > (+,-,*,/,&,|,^)
        System.out.println(rsl);
        System.out.println(i);
        int i0 = 1;
        i0 = i0++; //i0先放到操作数栈中 然后再进行自增 再赋值给i0
        System.out.println(i0); //1

        int a, b;
        a = b = 20;
        while (a++ % 3 != 0 | ++a % 7 != 0) {
            System.out.println((a - 2) + "不能被3整除;" + a + "不能被7整除;");
        }
        System.out.println(a);
        while (b++ % 3 != 0 || ++b % 7 != 0) {
            System.out.println((b - 2) + "不能被3整除;" + b + "不能被7整除;");
        }
        System.out.println(b);

    }
}
