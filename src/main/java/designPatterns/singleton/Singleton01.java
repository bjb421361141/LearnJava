package designPatterns.singleton;

import java.util.Random;

/**
 * 简单的单例模式（饿汉式） -- 常用
 * 缺点：类加载器一加载类，实例将被初始化
 */
public class Singleton01 {
    private static Singleton01 object = new Singleton01();

    private Singleton01() {
    }

    public static Singleton01 getInstance() {
        return object;
    }

    public static void main(String[] args) {
        for(int i=0; i<100; i++) {
            new Thread(()->{
                System.out.println(Singleton01.getInstance().hashCode());
            }).start();
        }
    }
}
