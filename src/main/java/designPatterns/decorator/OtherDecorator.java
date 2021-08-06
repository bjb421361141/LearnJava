package designPatterns.decorator;

import designPatterns.decorator.inf.MyShape;

public class OtherDecorator implements MyShape {
    MyShape innerSharp;

    public OtherDecorator(MyShape innerSharp) {
        this.innerSharp = innerSharp;
    }

    @Override
    public void draw() {
        innerSharp.draw();
        System.out.println("add a OtherSharp");
    }
}
