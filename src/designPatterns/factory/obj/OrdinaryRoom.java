package designPatterns.factory.obj;

import designPatterns.factory.inf.Room;

public class OrdinaryRoom extends Room {
    @Override
    public void enter() {
        System.out.println("进入普通的房间");
    }
}
