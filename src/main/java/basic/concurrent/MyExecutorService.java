package basic.concurrent;

import java.lang.reflect.Method;
import java.util.ArrayList;
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

    void submit(Callable<String> callable) {
        futureList.add(pool.submit(callable));
    }

    void submit(Object targetObject, Method method, Object... args) {
        if (targetObject == null || method == null) {
            throw new RuntimeException("构建线程池异常");
        }
        String callerNm = targetObject.getClass().toString() + "@" + targetObject.hashCode();
        futureList.add(pool.submit(new MyCallable(callerNm, targetObject, method, args)));
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
     * @param runnable 可执行对象
     * @param initialDelay 延时长度
     * @param period  执行周期（多久一次）
     * @param unit 延时单位
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
            System.out.println("ThreadName:" + Thread.currentThread().getName() + "@" + System.currentTimeMillis());
            System.out.println("callerNm:" + this.callerNm + "-" + this.hashCode());
            if (method != null && targetObject != null) {
                Object result = method.invoke(targetObject, args);
                return result.toString();
            }
            return "callerNm" + this.callerNm;
        }
    }
}
