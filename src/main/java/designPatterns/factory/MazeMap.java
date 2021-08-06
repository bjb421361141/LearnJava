package designPatterns.factory;

import designPatterns.factory.inf.Door;
import designPatterns.factory.inf.Mapsite;
import designPatterns.factory.inf.Room;


public class MazeMap {
    public static String DEFAULT = "Default";
    /**
     * 当传入不同的factory 就生成不同的factory
     * @return 入口对象
     */
    Mapsite createMazeMap(RoomAbstractFactory factory) {
        Room r1 = factory.getRoom("1001",DEFAULT);
        Room r2 = factory.getRoom("1002",DEFAULT);
        Door door = factory.getDoor(r1,r2,DEFAULT);
        r1.setSide(Room.Direction.East,door);
        r2.setSide(Room.Direction.West,door);
        return r1;
    }

    public static void main(String[] args){
        MagicRoomFactory factory =  new MagicRoomFactory();
        MazeMap mazeMap = new MazeMap();
        Mapsite mapsite = mazeMap.createMazeMap(factory);
        OrdinaryRoomFactory ordinaryFactory =  new OrdinaryRoomFactory();
        MazeMap ordinaryMazeMap = new MazeMap();
        Mapsite ordinaryMapsite = mazeMap.createMazeMap(ordinaryFactory);


    }
}
