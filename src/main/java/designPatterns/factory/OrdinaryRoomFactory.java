package designPatterns.factory;

import designPatterns.factory.inf.Door;
import designPatterns.factory.inf.Room;
import designPatterns.factory.inf.Wall;
import designPatterns.factory.obj.BombRoom;
import designPatterns.factory.obj.OrdinaryDoor;
import designPatterns.factory.obj.OrdinaryRoom;
import designPatterns.factory.obj.OrdinaryWall;

/**
 * 简单工厂
 * 生产一系列普通房间
 */
public class OrdinaryRoomFactory extends RoomAbstractFactory {

    @Override
    public Room getRoom(String roomnum, String roomType) {
        Room r = null;
        switch (roomType) {
            case "Bomb":r = new BombRoom();
            case "Default":r = new OrdinaryRoom();
        }
        return r;
    }

    @Override
    public Wall getWall(String wallType) {
        return new OrdinaryWall();
    }

    @Override
    public Door getDoor(Room r1, Room r2, String doorType) {
        return new OrdinaryDoor();
    }
}
