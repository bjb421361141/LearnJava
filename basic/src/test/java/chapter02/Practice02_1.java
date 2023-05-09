package chapter02;

import java.util.Stack;

public class Practice02_1 {
    public static void main(String[] args) {
        byte b1, b2;
        b1 = 10;
        b2 = 20;
        byte b = (byte) (b1 + b2);
        System.out.println("b1 和 b2的和为" + b);

        short s1 = 100, s2 = 200;
        short s = (short) (s1 * s2);
        System.out.println("s1 和 s2的乘积是" + s);

        char c1 = 'a';
        int i = 30;
        System.out.println("c1 和 i 的差值为" + (c1 - 30));

        for (int j = 10; j < 14; j++) {
            if (j % 2 == 0) {
                System.out.println(j + " is even");
            } else {
                System.out.println(j + " is odd");
            }
        }

        int liu = 23;
        int guan = 22;
        int zhang = 21;
        int tmpMax = liu > guan ? liu : guan;
        int finalMax = tmpMax > zhang ? tmpMax : zhang;
        System.out.println("年龄最大的是" + finalMax + "岁");

        System.out.println(integer2BinaryStr(60));
        System.out.println(integer2HexStr(60));
        System.out.println(integer2HexStr2(60));

        int year = 2000;
        boolean leapYear = isLeapYear(year);

    }

    private static boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        } else if (year % 400 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 将2进制码存储到栈对象中
     *
     * @param integer 只允许正数
     * @return
     */
    private static Stack<Integer> handleBinaryCode(int integer) {
        boolean negativeFlag = false;
        if (integer < 0) {
            negativeFlag = true;
            integer = Math.abs(integer); //取绝对值
        }
        Stack<Integer> binaryStack = new Stack<>();
        do {
            binaryStack.push(integer % 2);
            integer = (integer - (integer % 2)) / 2;
        } while (integer != 0);
        while (binaryStack.size() < 31) { //除符号位其余都置为0
            binaryStack.push(0);
        }
        if (negativeFlag) {
            //先取反再加1
            Stack<Integer> tmpStack = new Stack<>();
            while (!binaryStack.empty()) {
                tmpStack.push(binaryStack.pop() == 0 ? 1 : 0);
            }
            boolean addStopFlag = false;
            while (!tmpStack.empty()) {
                Integer t = tmpStack.pop();
                if (!addStopFlag) {
                    if (t == 1) {
                        t = 0;
                    } else {
                        t = 1;
                        addStopFlag = true;
                    }
                }
                binaryStack.push(t);
            }
        }
        binaryStack.push(negativeFlag ? 1 : 0);
        return binaryStack;
    }

    public static String integer2BinaryStr(int integer) {
        Stack<Integer> binaryStack = handleBinaryCode(integer);
        StringBuffer sb = new StringBuffer();
        while (!binaryStack.empty()) {
            sb.append(binaryStack.pop());
        }
        return sb.toString();
    }

    public static String integer2HexStr(int integer) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        Stack<Integer> binaryStack = handleBinaryCode(integer);
        int count = 0;
        int m = 8;
        int idx = 0;
        StringBuffer sb = new StringBuffer();
        boolean startRecord = false;
        while (!binaryStack.empty()) {
            idx += binaryStack.pop() * m;
            if (idx > 0) {
                startRecord = true;
            }
            count++;
            if (count % 4 == 0) {
                if (startRecord) {
                    sb.append(hexChars[idx]);
                }
                count = 0;
                m = 8;
                idx = 0;
            } else {
                m /= 2;
            }
        }
        return sb.toString();
    }

    /**
     * 通过位运算来进行数据的转换
     *
     * @param integer
     * @return
     */
    public static String integer2HexStr2(int integer) {
        int count = 0;
        String[] tmpChar = new String[8];
        while (count < 8) {
            int toConvertNum = integer & 15;
            tmpChar[count] = (toConvertNum > 9 ? (char) (toConvertNum - 10 + 'a') + "" : toConvertNum + "");
            integer >>>= 4; //右移动4位
            count++;
        }
        boolean startRecode = false;
        StringBuffer sb = new StringBuffer();
        for (int i = 7; i >= 0; i--) {
            if (!"0".equals(tmpChar[i])) {
                startRecode = true;
            }
            if (startRecode) {
                sb.append(tmpChar[i]);
            }
        }
        return sb.toString();
    }
}
