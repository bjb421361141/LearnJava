package concurrent;

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

    /**
     * 通过Builder 来构建配置
     *
     * @param poolType
     */
    private PoolConfiguration(PoolType poolType) {
        this.poolType = poolType;
    }

    public PoolType getPoolType() {
        return poolType;
    }

    public int getPoolsize() {
        return poolsize;
    }

    enum PoolType {
        /**
         * 缓存池类型
         */
        CachedThreadPool, FixedThreadPool, ScheduledThreadPool, SingleThreadExecutor
    }

    enum Field {
        /**
         * 相关配置属性
         */
        poolsize
    }

    public static class Builder {
        PoolConfiguration poolConfiguration;

        Builder(PoolType poolType) {
            poolConfiguration = new PoolConfiguration(poolType);
        }

        Builder add(Field field, Object value) throws Exception {
            switch (field) {
                case poolsize:
                    this.poolConfiguration.poolsize = Integer.parseInt(value.toString());
                    break;
                default:
                    throw new Exception("没有相关字段设置");
            }
            return this;
        }

        public PoolConfiguration getPoolConfiguration() {
            return poolConfiguration;
        }
    }
}
