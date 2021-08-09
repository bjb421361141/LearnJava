package basic.concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 线程类实现
 * 调用 start 方法线程进入到就绪状态(调用native start0 方法)
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
        System.out.println("Thread name "+ Thread.currentThread().getName());
        System.out.println("run 方法只是线程被激活后调用的方法，不要直接使用！");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (!exit){
            try {
                String str = bufferedReader.readLine();
                //do something
                if("quit".equals(str)) {
                    exit = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("主线程："+Thread.currentThread().getName());
        MyThread myThread = new MyThread("线程1");
        //myThread.run();  //仅是方法调用 线程还是主线程在跑
        System.out.println("---------------------------------");
        myThread.start();
        System.out.println("------------线程终止方法-------------------");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        for (;;) {
            try {
                String str = bufferedReader.readLine();
                //do something
                if("quit".equals(str)) {
                    myThread.exit = true;
                } else if("aboard".equals(str)) {

                } else if("interrupt".equals(str)) {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
