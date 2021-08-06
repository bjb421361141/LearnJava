package basic.concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static basic.concurrent.PoolConfiguration.PoolType.*;

/**
 * 整合线程池操作类别
 * ThreadPoolExecutor ： CAS 和 ReentrantLock 进行并发操作的控制
 * 使用CAS进行计数，记完数后生成Worker类（包含了Thread的内部类）
 *
 */
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
//        //创建一个线程池
////        PoolConfiguration poolConfiguration = new PoolConfiguration(FixedThreadPool, 3);
//        PoolConfiguration poolConfiguration = new PoolConfiguration(CachedThreadPool,3);//没有用到size
////        PoolConfiguration poolConfiguration = new PoolConfiguration(ScheduledThreadPool,3);
////        PoolConfiguration poolConfiguration = new PoolConfiguration(SingleThreadExecutor,3);//没有用到size
//
//        MyExecutorService pool = new MyExecutorService(poolConfiguration);
//        for (int i = 0; i < 5; i++) {
//            pool.submit(i + " "); // 执行任务并获取 Future 对象
//        }
//        pool.shutdown(); // 关闭线程池
//        for (Future<String> f : pool.getFutureList()) { // 获取所有并发任务的运行结果
//            System.out.println("res：" + f.get());
//        }


        //Executor中 将状态和工作的线程数打包成 Int 32位 放入 AtomicInteger对象中 ：前3位为状态位，后29位为当前工作数
        // 和 CAPACITY   进行 &运算获得 工作数
        // 和 ~CAPACITY  进行 &运算获得 main线程的工作状态
        System.out.println(Integer.toBinaryString((1 << 29) - 1));//     00011111111111111111111111111111   CAPACITY
        System.out.println(Integer.toBinaryString((-1 << 29)));//           11100000000000000000000000000000   RUNNING
        System.out.println(Integer.toBinaryString((0 << 29))); //           00000000000000000000000000000000   SHUTDOWN
        System.out.println(Integer.toBinaryString((1 << 29))); //           00100000000000000000000000000000   STOP
        System.out.println(Integer.toBinaryString((2 << 29))); //           01000000000000000000000000000000   TIDYING
        System.out.println(Integer.toBinaryString((3 << 29))); //           01100000000000000000000000000000   TERMINATED


    }
}
