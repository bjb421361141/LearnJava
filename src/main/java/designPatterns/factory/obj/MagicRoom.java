package designPatterns.factory.obj;

import designPatterns.factory.inf.Room;

public class MagicRoom extends Room {
    @Override
    public void enter() {
        System.out.println("魔法房间");
    }
}
