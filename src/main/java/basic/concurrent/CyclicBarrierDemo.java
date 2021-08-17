package basic.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 回环栅栏
 * <p>
 * CountDownLatch与CyclicBarrier:
 * CountDownLatch是一个同步的辅助类，允许一个或多个线程，等待其他一组线程完成操作，再继续执行。
 * CyclicBarrier是一个同步的辅助类，允许一组线程相互之间等待，达到一个共同点，再继续执行。
 * 区别:
 * <p>
 * CountDownLatch的计数器只能使用一次。而CyclicBarrier的计数器可以使用reset() 方法重置。所以CyclicBarrier能处理更为复杂的业务场景，比如如果计算发生错误，可以重置计数器，并让线程们重新执行一次
 * CyclicBarrier还提供getNumberWaiting(可以获得CyclicBarrier阻塞的线程数量)、isBroken(用来知道阻塞的线程是否被中断)等方法。
 * CountDownLatch会阻塞主线程，CyclicBarrier不会阻塞主线程，只会阻塞子线程。
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int wrterNums = 2;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(wrterNums + 1, new Runnable() {
            @Override
            public void run() {
                System.out.println("拦截后调用该线程进行后续操作");
            }
        });
        for (int i = 0; i < 4; i++) {
            new Writer("Thread-" + i, cyclicBarrier).start();
        }
    }

    static class Writer extends Thread {
        CyclicBarrier cyclicBarrier;

        public Writer(String threadNm, CyclicBarrier cyclicBarrier) {
            super(threadNm);
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                sleep(2000);
                System.out.println(Thread.currentThread().getName() + "done jobs");
                cyclicBarrier.await(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

}
