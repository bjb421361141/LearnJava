package factory.obj;

import factory.inf.Room;

import java.util.HashMap;

import static factory.inf.Room.Direction.East;
import static factory.inf.Room.Direction.North;
import static factory.inf.Room.Direction.South;
import static factory.inf.Room.Direction.West;

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
