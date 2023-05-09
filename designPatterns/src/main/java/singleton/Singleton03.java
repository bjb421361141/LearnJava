package singleton;

/**
 * 静态内部类 单例模式
 */
public class Singleton03 {
    private static Singleton03 singleton02 = null;

    private Singleton03() {
    }

    public static Singleton03 getInstance() {
        return Singleton03Holder.INSTANCE;
    }

    /**
     * 静态内部类中进行初始化，好处是当Singleton3被类加载器加载完后不会立即实例化
     */
    private static class Singleton03Holder {
        private final static Singleton03 INSTANCE = new Singleton03();
    }

    public static void main(String[] args) {
        for(int i=0; i<100; i++) {
            new Thread(()->{
                System.out.println(Singleton03.getInstance().hashCode());
            }).start();
        }
    }
}
