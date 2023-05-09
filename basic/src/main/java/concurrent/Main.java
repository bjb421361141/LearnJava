package concurrent;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static concurrent.PoolConfiguration.PoolType.FixedThreadPool;
import static concurrent.PoolConfiguration.PoolType.SingleThreadExecutor;

/**
 * 整合线程池操作类别
 * ThreadPoolExecutor ： CAS 和 ReentrantLock 进行并发操作的控制
 * 使用CAS进行计数，记完数后生成Worker类（包含了Thread的内部类）
 */
public class Main {
    static int taskSize = 5;

    public static void main(String[] args) throws ExecutionException, InterruptedException, NoSuchMethodException {
        //创建一个线程池
        PoolConfiguration poolConfiguration = null;
        try {
            poolConfiguration = new PoolConfiguration.Builder(FixedThreadPool).add(PoolConfiguration.Field.poolsize, "3").getPoolConfiguration();
//            poolConfiguration = new PoolConfiguration.Builder(CachedThreadPool).getPoolConfiguration();
//            poolConfiguration = new PoolConfiguration.Builder(ScheduledThreadPool).add(PoolConfiguration.Field.poolsize,"3").getPoolConfiguration();
//            poolConfiguration = new PoolConfiguration.Builder(SingleThreadExecutor).getPoolConfiguration();  //
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyExecutorService pool = new MyExecutorService(poolConfiguration);
        for (int i = 0; i < taskSize; i++) {  //第2的倍数任务一起执行
            if (i % 2 == 0 && poolConfiguration.getPoolType() != SingleThreadExecutor) {
                MyTask myTask = new MyTask();
                Method method = myTask.getClass().getMethod("doMyJob", String.class);
                pool.submit(myTask,method,i/2);
            } else {
                pool.submit(i + " "); // 执行任务并获取 Future 对象
            }
        }
        // 等待线程跑完后再进行关闭
        pool.shutdown(); // 关闭线程池
        for (Future<?> f : pool.getFutureList()) { // 获取所有并发任务的运行结果
            System.out.println("res：" + f.get());
        }
    }
}
