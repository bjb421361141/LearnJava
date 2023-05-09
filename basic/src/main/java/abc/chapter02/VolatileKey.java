package abc.chapter02;

import java.util.concurrent.TimeUnit;

/**
 * JVM 内存模型（JMM）
 * .java 文件-->.class文件-->JVM使用classloader加载.class 文件 -->使用字节码解释器或JTI（即时编译器）进行指令的执行
 * 内存布局：
 * 本地方法栈： 执行native 方法
 * 虚拟机栈： 执行java方法
 * 程序计数器：记录当前线程所执行的字节码的行号指示器
 * 本地内存：
 * 方法区：方法区是所有线程共享的内存,jdk1.8后采用元空间实现从永久区剥离直接放入本地内存，不受JVM内存影响，受机器物理内存影响
 * 1、方法区中主要存储的是一些类的元数据（类的方法代码，变量名，方法名，访问权限，返回值等等都是在方法区）
 * 2、fixme 待拓展
 * 直接内存：本地内存中可以直接被使用的空间，不受JVM内存影响，受机器物理内存影响
 *
 * 在同一线程内，指令是有序的，在不同线程上，指令是无序的（指标重排）
 */
public class VolatileKey {

    int counter = 0;

    /**
     * volatile的特殊规则：
     *          保证变量的可见性、禁止指令重新排列优化
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        VolatileKey vk = new VolatileKey();
        vk.new ChangeListener().start();
        vk.new ChangeMaker().start();
    }

    class ChangeListener extends Thread {
        @Override
        public void run() {
            int threadValue = counter;
            while (threadValue < 5) {
                if (threadValue != counter) {
                    System.out.println("监听到counter值改变了" + counter);
                    threadValue = counter;
                }
            }
        }
    }

    class ChangeMaker extends Thread {
        @Override
        public void run() {

            int threadValue = counter;
            while (threadValue < 5) {
                System.out.println("值小于5增加1:" + (threadValue + 1));
                counter = ++threadValue;
                try {
                    Thread.sleep(500);
//                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
