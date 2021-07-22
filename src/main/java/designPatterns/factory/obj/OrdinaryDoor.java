package designPatterns.factory.obj;

import designPatterns.factory.inf.Door;

public class OrdinaryDoor extends Door {
    @Override
    public void enter() {
        System.out.println("普通的门");
    }
}
