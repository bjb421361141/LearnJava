package concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 线程类实现
 * 调用 start 方法线程进入到就绪状态(调用native start0 方法)
 * <p>
 * 线程结束的方法：
 * 1.修改控制线程的标志位使得线程
 */
public class MyThread extends Thread {
    public volatile boolean exit = false; //控制线程终止标志
    String myName;

    MyThread(String threadName) {
        super(threadName);
        this.myName = threadName;
    }

    @Override
    public void run() {
        System.out.println("Thread name " + Thread.currentThread().getName());
        System.out.println("run 方法只是线程被激活后调用的方法，不要直接使用！");
        while (!exit) {  //这边也可以使用 this.isInterrupted() 中的标志位 和 interrupt()来控制线程的终止
            try {
                System.out.println("子线程 is running！");
                sleep(Long.parseLong("3000"));
                //doSomeWork
            } catch (Exception e) {
                System.out.println("线程捕获InterruptException弹出");
                break; //这边来处理异常弹出
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        System.out.println("主线程：" + mainThread.getName());
        MyThread myThread = new MyThread("线程1");
        //myThread.run();  //仅是方法调用 线程还是主线程在跑
        System.out.println("------------子线程启动---------------------");
        myThread.start();

        System.out.println("------------线程终止方法-------------------");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        for (; ; ) {
            try {
                System.out.println("主计划线程输入:");
                String str = bufferedReader.readLine();
                if ("quit".equals(str)) {  //1.修改标志位使得线程执行完毕
                    myThread.exit = true;
                } else if ("interrupt".equals(str)) { //2.调用interrupt() 使得线程抛出异常
                    myThread.interrupt();
                } else if ("stop".equals(str)) { //3.不建议使用 释放线程上所有锁可能会导致数据不一致的问题
                    myThread.stop();
                } else if ("status".equals(str)){
                    System.out.println("当前子线程状态：" + myThread.isAlive());
                } else if ("end".equals(str)) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
