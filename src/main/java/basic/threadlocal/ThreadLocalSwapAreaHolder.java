package basic.threadlocal;

import basic.threadlocal.inf.SwapArea;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal 不保存数据只将数据往当前线程的ThreadLocalMap存放一个key为自身 值为 value的记录，各个线程保存各自信息
 */
public class ThreadLocalSwapAreaHolder {
    ThreadLocal<SwapArea> holder = new ThreadLocal<SwapArea>();

    void setCurrentSwapArea(SwapArea swapArea) {
        this.holder.set(swapArea);
    }

    SwapArea getCurrentSwapArea() {
        return this.holder.get();
    }

    SwapArea removeCurrentSwapArea() {
        SwapArea tmp = this.getCurrentSwapArea();
        this.holder.remove();
        return tmp;
    }

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ThreadLocal<Map<String, String>> holder1 = new ThreadLocal<>();
        Map<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        concurrentHashMap.put("main", "this is main");
        holder1.set(concurrentHashMap);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1执行");
                concurrentHashMap.put("thread1", "this is thread1");
                holder1.set(concurrentHashMap);
                System.out.println("线程1 holder1 是否含有main: " + holder1.get().containsKey("main"));
                countDownLatch.countDown();
                for (; ; ) { //循环获取属于线程1 的数据打印并移除 ; 验证子线程也可以获取其他线程的设置的数据
                    if (holder1.get().containsKey("thread2")) {
                        System.out.println("线程2 中设置的值" + holder1.get().get("thread2"));
                        holder1.get().remove("thread2");
                        break;
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程2执行");
                    Thread.currentThread().sleep(Long.parseLong("1000"));
                    concurrentHashMap.put("thread2", "this is thread2");
                    holder1.set(concurrentHashMap);
                    System.out.println("线程2 holder1 是否含有main: " + holder1.get().containsKey("main"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
                for (; ; ) { //等待主线程的停止指令
                    if (holder1.get().containsKey("stop_thread2")) {
                        System.out.println("收到主线程中的数据：" + holder1.get().get("stop_thread2"));
                        break;
                    }
                }
            }
        }).start();
        System.out.println("----------------------------等待线程计数完成后继续执行----------------------------");
        countDownLatch.await(Long.parseLong("20"), TimeUnit.SECONDS);
        System.out.println("主线程中 是否含有thread1: " + holder1.get().containsKey("thread1")); //说明线程之间的变量不会互相影响
        System.out.println(holder1.get());
        holder1.get().put("stop_thread2", "停止线程2");
    }
}
