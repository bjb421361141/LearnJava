package designPatterns.factory.obj;

import designPatterns.factory.inf.Room;

import java.util.HashMap;

import static designPatterns.factory.inf.Room.Direction.East;
import static designPatterns.factory.inf.Room.Direction.North;
import static designPatterns.factory.inf.Room.Direction.NorthEast;
import static designPatterns.factory.inf.Room.Direction.NorthWest;
import static designPatterns.factory.inf.Room.Direction.South;
import static designPatterns.factory.inf.Room.Direction.SouthEast;
import static designPatterns.factory.inf.Room.Direction.SouthWest;
import static designPatterns.factory.inf.Room.Direction.West;

public class MagicRoom extends Room {
    @Override
    public void enter() {
        System.out.println("魔法房间");
    }

    @Override
    protected void initSiteInfMap() {
        siteInfMap = new HashMap<>();
        siteInfMap.put(North,new MagicWall());
        siteInfMap.put(South,new MagicWall());
        siteInfMap.put(East,new MagicWall());
        siteInfMap.put(West,new MagicWall());
        siteInfMap.put(SouthEast,new MagicWall());
        siteInfMap.put(SouthWest,new MagicWall());
        siteInfMap.put(NorthEast,new MagicWall());
        siteInfMap.put(NorthWest,new MagicWall());
    }
}
