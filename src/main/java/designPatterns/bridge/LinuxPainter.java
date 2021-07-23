package designPatterns.bridge;

import designPatterns.bridge.inf.Painter;

public class LinuxPainter implements Painter {

    @Override
    public void draw() {
        System.out.println("实现Linux系统下的画笔操作");
    }
}
