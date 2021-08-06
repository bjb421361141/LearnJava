package designPatterns.decorator;

import designPatterns.decorator.inf.MyShape;

/**
 * 三角形装饰器
 * <p>
 * 不改变原有结构的基础上增加新功能
 */
public class TriangleDecorator implements MyShape {
    MyShape innerSharp;

    public TriangleDecorator(MyShape innerSharp) {
        this.innerSharp = innerSharp;
    }

    @Override
    public void draw() {
        innerSharp.draw();
        System.out.println("add a Triangle");
    }
}
