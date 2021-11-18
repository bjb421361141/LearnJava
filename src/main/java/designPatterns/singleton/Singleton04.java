package designPatterns.singleton;

/**
 * 枚举类型实现单例模式
 *
 *   通过调用枚举的方法来获取实例对象
 */
public class Singleton04 {

    private Singleton04() {
    }

    /**
     * 枚举类型是线程安全的，并且只会装载一次
     */
    private enum Singleton {
        INSTANCE;

        private final Singleton04 instance;

        Singleton() { //枚举类的构造方法
            instance = new Singleton04();
        }

        private Singleton04 getInstance() {
            return instance;
        }
    }

    public static Singleton04 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public static void main(String[] args) {
        for(int i=0; i<100; i++) {
            new Thread(()->{
                System.out.println(Singleton04.getInstance().hashCode());
            }).start();
        }
    }
}