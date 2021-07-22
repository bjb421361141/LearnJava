package designPatterns.factory.obj;

import designPatterns.factory.inf.Room;

public class BombRoom extends Room {
    @Override
    public void enter() {
        System.out.println("你有3秒可以逃跑");
    }
}
