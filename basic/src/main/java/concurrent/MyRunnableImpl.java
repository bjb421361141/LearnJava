package concurrent;

public class MyRunnableImpl implements Runnable {
    @Override
    public void run() {
        System.out.println("对于 实现runnerable 接口的 。 run 方法只是线程被激活后调用的方法，不要直接使用！");
    }

    public static void main(String[] args) {
        MyRunnableImpl runnable = new MyRunnableImpl();
        new Thread(runnable).start();  //使用 Thread中的start 进行线程启动
    }
}
