package decorator;

import decorator.impl.Circle;
import decorator.impl.Rectangle;
import decorator.inf.MyShape;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {
    static final String CIRCLE_LABEL = "circle";
    static final String RECTANGLE_LABEL = "rectangle";

    static Map<String, MyShape> shapeMap = new HashMap<String, MyShape>();

    static Circle getCircle(String color) {
        if (shapeMap.containsKey(CIRCLE_LABEL + color)) {
            return (Circle) shapeMap.get(CIRCLE_LABEL + color);
        } else {
            Circle circle = new Circle(color);
            shapeMap.put(CIRCLE_LABEL + color, circle);
            return circle;
        }
    }

    static Rectangle getRectangle(String color) {
        if (shapeMap.containsKey(RECTANGLE_LABEL + color)) {
            return (Rectangle) shapeMap.get(RECTANGLE_LABEL + color);
        } else {
            Rectangle rectangle = new Rectangle(color);
            shapeMap.put(RECTANGLE_LABEL + color, rectangle);
            return rectangle;
        }
    }
}
