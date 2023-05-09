package singleton;

/**
 * lambada 表达式
 * 写法：
 * (parameters) -> expression
 * 或
 * (parameters) ->{ statements; }
 */
public class Main {

    private static void main() {
        //相当于 实现一个接口
        MathOperation addition = (int a, int b) -> {return a + b; };  //方法体内容 a+b
        System.out.println(Main.operate(1, 2, addition));
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    /**
     * 相当于绕了一圈 外部不直接调用实现的接口
     *
     * @param a             参数1
     * @param b             参数2
     * @param mathOperation 实现接口的对象（动态实现的接口对象）
     * @return 返回值
     */
    public static int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}
