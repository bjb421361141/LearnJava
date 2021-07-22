package designPatterns.factory;

import designPatterns.factory.inf.Door;
import designPatterns.factory.inf.Room;
import designPatterns.factory.inf.Wall;

/**
 * 抽象工厂
 * 统一规范产品簇接口，具体实现产品由子工厂来实现
 */
public abstract class RoomAbstractFactory {
    public abstract Room getRoom(String roomnum, String roomType);

    public abstract Wall getWall(String wallType);

    public abstract Door getDoor(Room r1,Room r2,String doorType);
}
