package designPatterns.factory.inf;

import java.util.Map;

public abstract class Room extends Mapsite {
    String roomNum;
    Map<Direction, Mapsite> siteInfMap;

    void Room(String roomNum) {
        this.roomNum = roomNum;
    }

    public void setSide(Direction direction, Mapsite mapsite) {
        siteInfMap.put(direction, mapsite);
    }

    /**
     * 返回方位信息
     *
     * @param direction
     * @return
     */
    public Mapsite getSide(Direction direction) {
        return siteInfMap.get(direction);
    }

    public enum Direction {
        North, South, East, West
    }
}
