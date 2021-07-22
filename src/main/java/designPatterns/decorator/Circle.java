package designPatterns.decorator;

import designPatterns.decorator.inf.Sharp;

public class Circle implements Sharp {
    @Override
    public void draw() {
        System.out.println("Draw a Circle.");
    }
}
