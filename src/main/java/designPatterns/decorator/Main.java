package designPatterns.decorator;

public class Main {
    public static void main(String[] args){
        Retangle retangle = new Retangle();
        TriangleDecorator triangleDecorator = new TriangleDecorator(retangle);
        OtherDecorator otherDecorator = new OtherDecorator(triangleDecorator);

        otherDecorator.draw();
    }
}
