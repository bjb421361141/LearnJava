package designPatterns.factory.inf;

import java.util.Map;

public abstract class Room extends Mapsite {
    String roomNum;
    protected Map<Direction, Mapsite> siteInfMap;

    public void Room(String roomNum) {
        this.roomNum = roomNum;
    }

    public void setSide(Direction direction, Mapsite mapsite) {
        if (siteInfMap == null) {
            initSiteInfMap();
        }
        if (siteInfMap.containsKey(direction)) {  //存在访问信息才进行设置
            siteInfMap.put(direction, mapsite);
        }
    }

    /**
     * 初始化方位的信息
     */
    protected abstract void initSiteInfMap();

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
        North, South, East, West, SouthEast, SouthWest, NorthEast, NorthWest
    }
}
