package _01_anno;

import org.springframework.beans.factory.annotation.Value;

public class Cat {

    private String name;

    @Value("aa")
    public Cat setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                '}';
    }
}
