package designPatterns.factory.inf;

public abstract class Door extends Mapsite{
    Room r1;
    Room r2;
    boolean isOpened = false;

    void Door(Room r1,Room r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    @Override
    public void enter() {
        System.out.println("这是门可以打开！");
    }
}
