package decorator.impl;

import decorator.inf.MyShape;

public class Rectangle implements MyShape {
    String color;

    public Rectangle(String color) {
        this.color = color;
    }

    @Override
    public void draw() {
        System.out.println("Draw a Retangle.");
    }
}
