package factory;

import factory.inf.Door;
import factory.obj.MagicDoor;
import factory.obj.MagicRoom;
import factory.inf.Room;
import factory.inf.Wall;
import factory.obj.MagicWall;

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
        return new MagicWall();
    }

    @Override
    public Door getDoor(Room r1, Room r2, String doorType) {
        return new MagicDoor(r1,r2);
    }
}
