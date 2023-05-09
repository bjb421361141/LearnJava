package decorator;

import decorator.impl.Circle;
import decorator.impl.Rectangle;

/**
 * decorator 模式 在不改变原有类结构条件下对类进行拓展
 * 使用聚合一个要拓展的类来实现
 * <p>
 * 享元模式：主要用于减少创建对象的数量，以减少内存占用和提高性能。使用工厂类进行控制。
 */
public class Main {

    private static final Color colors[] = {Color.RED, Color.BLUE, Color.GREEN};

    private static Color getRandomColor() {
        return colors[(int) (Math.random() * colors.length)];
    }

    private static int getRandomX() {
        return (int) (Math.random() * 100);
    }

    private static int getRandomY() {
        return (int) (Math.random() * 100);
    }

    public static void main(String[] args) {
        Rectangle retangle = new Rectangle(Color.RED.getValue());
        TriangleDecorator triangleDecorator = new TriangleDecorator(retangle);
        OtherDecorator otherDecorator = new OtherDecorator(triangleDecorator);
        otherDecorator.draw();

        for (int i = 0; i < 20; ++i) {
            Circle circle = (Circle) ShapeFactory.getCircle(getRandomColor().getValue());
            circle.setX(getRandomX());
            circle.setY(getRandomY());
            circle.setRadius(100);
            circle.draw();
        }
    }

    enum Color {
        RED("red"), BLUE("blue"), GREEN("green");
        String value;

        Color(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
