package designPatterns.factory;

import designPatterns.factory.inf.Door;
import designPatterns.factory.inf.Mapsite;
import designPatterns.factory.inf.Room;


public class MazeMap {
    public static String DEFAULT = "Default";
    /**
     * 当传入不同的factory 就生成不同的factory
     * @return
     */
    Mapsite createMazeMap(RoomAbstractFactory factory) {
        Room r1 = factory.getRoom("1001",DEFAULT);
        Room r2 = factory.getRoom("1002",DEFAULT);
        Door door = factory.getDoor(r1,r2,DEFAULT);
        r1.setSide(Room.Direction.East,door);
        factory.getDoor(r1,r2,DEFAULT);
        return r1;
    }

    public static void main(){}
}
