package designPatterns.factory;

import designPatterns.factory.inf.Door;
import designPatterns.factory.obj.MagicRoom;
import designPatterns.factory.inf.Room;
import designPatterns.factory.inf.Wall;

/**
 * 生产一系列魔法房间
 */
public class MagicRoomFactory extends RoomAbstractFactory {

    @Override
    public Room getRoom(String roomnum, String roomType) {
        Room r = null;
        switch (roomType) {
            case "Default":r = new MagicRoom();
        }
        return r;
    }

    @Override
    public Wall getWall(String wallType) {
        return null;
    }

    @Override
    public Door getDoor(Room r1, Room r2, String doorType) {
        return null;
    }
}
