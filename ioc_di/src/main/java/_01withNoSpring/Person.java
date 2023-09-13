package _01withNoSpring;

public class Person {
    private String name;
    private Integer age;

    public String run(){
        return this.name+"开跑";
    }
    public Integer age() {
        return age;
    }

    public Person setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String name() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }
}
