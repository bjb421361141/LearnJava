package basic.collection.obj;

import designPatterns.singleton.Singleton03;

import java.util.Objects;

public class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {  //针对hash 类的操作测试插入情况
        return 12345;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return o.hashCode() ==  hashCode(); //Objects.equals(name, person.name);
    }

    @Override
    public String toString() {
        return "name:" + this.name;
    }
}
