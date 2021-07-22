package designPatterns.decorator;

import designPatterns.decorator.inf.Sharp;

public class OtherDecorator implements Sharp {
    Sharp innerSharp;

    public OtherDecorator(Sharp innerSharp) {
        this.innerSharp = innerSharp;
    }

    @Override
    public void draw() {
        innerSharp.draw();
        System.out.println("add a OtherSharp");
    }
}
