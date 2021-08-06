package designPatterns.factory.obj;

import designPatterns.factory.inf.Door;
import designPatterns.factory.inf.Room;

public class MagicDoor extends Door {
    public MagicDoor(Room r1, Room r2) {
        super.Door(r1,r2);
    }

    @Override
    public void enter() {
        System.out.println("魔法门");
    }
}
