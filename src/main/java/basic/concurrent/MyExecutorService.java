package basic.concurrent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static basic.concurrent.PoolConfiguration.PoolType.*;

/**
 * 使用线程池进行线程创建
 * Executor ：线程执行类
 */
public class MyExecutorService {
    private PoolConfiguration configuration;
    ExecutorService pool;
    List<Future<?>> futureList;

    MyExecutorService(PoolConfiguration configuration) { //这边可以传一个配置类对象
        this.initThreadPool(configuration);
    }

    private void initThreadPool(PoolConfiguration configuration) {
        futureList = new ArrayList<>();
        switch (configuration.poolType) {
            case CachedThreadPool:
                pool = Executors.newCachedThreadPool();
                break;
            case FixedThreadPool:
                pool = Executors.newFixedThreadPool(configuration.getPoolsize());
                break;
            case ScheduledThreadPool:
                pool = Executors.newScheduledThreadPool(configuration.getPoolsize());
                break;
            case SingleThreadExecutor:
                pool = Executors.newSingleThreadExecutor();
                break;
            default:
                throw new RuntimeException("构建线程池异常");
        }

    }

    void submit(String callerNm) {
        futureList.add(pool.submit(new MyCallable(callerNm)));
    }

    void submit(Callable<?> callable) {
        futureList.add(pool.submit(callable));
    }

    void submit(Runnable task) {
        futureList.add(pool.submit(task));
    }

    void submit(Object targetObject, Method method, Object... args) {
        if (targetObject == null || method == null) {
            throw new RuntimeException("构建线程池异常");
        }
        futureList.add(pool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
                System.out.println("ThreadName:" + Thread.currentThread().getName() + "@" + sdf.format(System.currentTimeMillis()));
                System.out.println("callerNm:" + targetObject.getClass().toString() + "@hashcode" + targetObject.hashCode());
                Object result = null;
                try {
                    result = method.invoke(targetObject, args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }));
    }


    /**
     * 可执行对象延时执行
     *
     * @param runnable 可执行对象
     * @param delay    延时长度
     * @param unit     延时单位
     */
    void schedule(Runnable runnable, long delay, TimeUnit unit) {
        // 延时执行的线程
        ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) pool;
        futureList.add(scheduledExecutorService.schedule(runnable, delay, unit));
    }

    /**
     * 循环执行定时任务
     *
     * @param runnable     可执行对象
     * @param initialDelay 延时长度
     * @param period       执行周期（多久一次）
     * @param unit         延时单位
     */
    void scheduleAtFixedRate(Runnable runnable, long initialDelay, long period, TimeUnit unit) {
        ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) pool;
        futureList.add(scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, period, unit));
    }


    void shutdown() {
        pool.shutdown();
    }

    public List<Future<?>> getFutureList() {
        return futureList;
    }


    private static class MyCallable implements Callable<String> {
        String callerNm;
        Object targetObject;
        Method method;
        Object[] args;

        public MyCallable(String callerNm) {
            this.callerNm = callerNm;
        }

        public MyCallable(String callerNm, Object targetObject, Method method, Object... args) {
            this.callerNm = callerNm;
            this.targetObject = targetObject;
            this.method = method;
            this.args = args;
        }

        @Override
        public String call() throws Exception {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
            System.out.println("ThreadName:" + Thread.currentThread().getName() + "@" + sdf.format(System.currentTimeMillis()));
            System.out.println("callerNm:" + this.callerNm + "@ hashcode:" + this.hashCode());
            if (method != null && targetObject != null) {
                Object result = method.invoke(targetObject, args);
                return result.toString();
            }
            return "callerNm" + this.callerNm;
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
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
