package designPatterns.factory.inf;

public abstract class Wall extends Mapsite{

    @Override
    public void enter() {
        System.out.println("这是墙不能走！");
    }
}
