package factory.obj;

import factory.inf.Wall;

public class MagicWall extends Wall {
    @Override
    public void enter() {
        System.out.println("魔法墙");
    }
}
