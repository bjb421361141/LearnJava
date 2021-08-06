package designPatterns.factory.obj;

import designPatterns.factory.inf.Room;
import designPatterns.factory.inf.Room.Direction.*;

import java.util.HashMap;

import static designPatterns.factory.inf.Room.Direction.North;
import static designPatterns.factory.inf.Room.Direction.South;
import static designPatterns.factory.inf.Room.Direction.East;
import static designPatterns.factory.inf.Room.Direction.West;

public class BombRoom extends Room {
    @Override
    public void enter() {
        System.out.println("你有3秒可以逃跑");
    }

    @Override
    protected void initSiteInfMap() {
        siteInfMap = new HashMap<>();
        siteInfMap.put(North,new OrdinaryWall());
        siteInfMap.put(South,new OrdinaryWall());
        siteInfMap.put(East,new OrdinaryWall());
        siteInfMap.put(West,new OrdinaryWall());
    }
}
