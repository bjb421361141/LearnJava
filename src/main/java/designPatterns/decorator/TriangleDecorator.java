package designPatterns.decorator;

import designPatterns.decorator.inf.Sharp;

/**
 * 三角形装饰器
 * <p>
 * 不改变原有结构的基础上增加新功能
 */
public class TriangleDecorator implements Sharp {
    Sharp innerSharp;

    public TriangleDecorator(Sharp innerSharp) {
        this.innerSharp = innerSharp;
    }

    @Override
    public void draw() {
        innerSharp.draw();
        System.out.println("add a Triangle");
    }
}
