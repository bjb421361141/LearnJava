package designPatterns.factory.obj;

import designPatterns.factory.inf.Wall;

public class OrdinaryWall extends Wall {
    @Override
    public void enter() {
        System.out.println("普通的墙");
    }
}
