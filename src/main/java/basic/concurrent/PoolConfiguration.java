package basic.concurrent;

/**
 * 用于配置MyExecutorService线程类的使用
 */
public class PoolConfiguration {
    PoolType poolType;
    private int poolsize;

    PoolConfiguration() {
        this.poolType = PoolType.SingleThreadExecutor;
        this.poolsize = 1;
    }

    public PoolConfiguration(PoolType poolType, int poolsize) {
        this.poolType = poolType;
        this.poolsize = poolsize;
    }

    public int getPoolsize() {
        return poolsize;
    }

    public void setPoolsize(int poolsize) {
        this.poolsize = poolsize;
    }

    enum PoolType {
        CachedThreadPool, FixedThreadPool, ScheduledThreadPool, SingleThreadExecutor
    }
}
