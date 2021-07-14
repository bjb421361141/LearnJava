package designPatterns.singleton;

/**
 * 双重确定 （懒汉式）单例模式
 * <p>
 * 缺点：效率下降
 * <p>
 * 编译器在用到这个变量时必须每次都小心地重新读取这个变量的值，而不是使用保存在寄存器里的备份。下面是volatile变量的几个例子
 * 1）并行设备的硬件寄存器（如：状态寄存器）
 * 2）一个中断服务子程序中会访问到的非自动变量（Non-automatic variables)
 * 3）多线程应用中被几个任务共享的变量
 */
public class Singleton02 {
    private static volatile Singleton02 singleton02 = null;

    private Singleton02() {
    }

    public static Singleton02 getInstance() {
        if (singleton02 == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized(Singleton02.class) {
                if(singleton02 == null) {
                    singleton02 = new Singleton02();
                }
            }
        }
        return singleton02;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new MyThread("Thread" + i).start();
        }
    }
}

class MyThread extends Thread {
    public MyThread(String threadName) {
        super(threadName);
    }

    @Override
    public void run() {
        Singleton02 instance = Singleton02.getInstance();
        System.out.println("本线程的名称：" + this.getName() + " hashcode :" + instance.hashCode());
    }
}
