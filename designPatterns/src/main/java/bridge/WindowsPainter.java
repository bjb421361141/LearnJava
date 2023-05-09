package bridge;

import bridge.inf.Painter;

public class WindowsPainter implements Painter {
    @Override
    public void draw() {
        System.out.println("实现windows系统下的画笔操作");
    }
}
