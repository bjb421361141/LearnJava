package designPatterns.factory.obj;

import designPatterns.factory.inf.Room;

import java.util.HashMap;

import static designPatterns.factory.inf.Room.Direction.East;
import static designPatterns.factory.inf.Room.Direction.North;
import static designPatterns.factory.inf.Room.Direction.South;
import static designPatterns.factory.inf.Room.Direction.West;

public class OrdinaryRoom extends Room {
    @Override
    public void enter() {
        System.out.println("进入普通的房间");
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
