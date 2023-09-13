package _02withSpring;

public class Student {
    private String name;

    public String say(){
        return this.name+"say Hello";
    }
    public String name() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    /*
    * 将Student的创建交给Spring
    * 怎么交给Spring?
    * 1. 通过配置文件
    * */
}
