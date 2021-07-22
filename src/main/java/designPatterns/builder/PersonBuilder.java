package designPatterns.builder;

/**
 * 简化复杂类的初始化工作
 */
public class PersonBuilder {
    private Person person;

    public PersonBuilder(String name, String age, String sex) {
        this.person = new Person(name, age, sex, null, null);
    }

    PersonBuilder addAddress(String address) {
        person.setAddress(address);
        return this;
    }

    PersonBuilder addHabits(String[] habits) {
        person.setHabits(habits);
        return this;
    }

    Person getPerson() {
        return this.person;
    }
}
