package designPatterns.factory.obj;

import designPatterns.factory.inf.Door;
import designPatterns.factory.inf.Room;

public class OrdinaryDoor extends Door {
    public OrdinaryDoor(Room r1, Room r2) {
        super.Door(r1, r2);
    }

    @Override
    public void enter() {
        System.out.println("普通的门");
    }
}
