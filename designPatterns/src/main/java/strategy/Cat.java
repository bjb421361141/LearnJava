package strategy;

public class Cat {
    String name;
    int height;
    int weight;

    Cat(String name ,int height, int weight) {
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
