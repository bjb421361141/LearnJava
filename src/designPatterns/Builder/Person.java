package designPatterns.Builder;

public class Person {
    String name;
    String age;
    String sex;
    String address;
    String[] habits;

    public Person(String name, String age, String sex, String address, String[] habits) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.habits = habits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getHabits() {
        return habits;
    }

    public void setHabits(String[] habits) {
        this.habits = habits;
    }
}
