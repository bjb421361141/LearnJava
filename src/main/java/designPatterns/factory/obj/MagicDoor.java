package designPatterns.factory.obj;

import designPatterns.factory.inf.Door;

public class MagicDoor extends Door {
    @Override
    public void enter() {
        System.out.println("魔法门");
    }
}
