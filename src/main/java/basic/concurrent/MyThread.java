package basic.concurrent;

/**
 * 线程类实现
 * 调用 start 方法线程进入到就绪状态(调用native start0 方法)
 */
public class MyThread extends Thread {
    String myName;

    MyThread(String threadName) {
        super(threadName);
        this.myName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Thread name "+ Thread.currentThread().getName());
        System.out.println("run 方法只是线程被激活后调用的方法，不要直接使用！");
    }

    public static void main(String[] args) {
        System.out.println("主线程："+Thread.currentThread().getName());
        MyThread myThread = new MyThread("线程1");
        myThread.run();
        System.out.println("---------------------------------");
        myThread.start();



    }
}
