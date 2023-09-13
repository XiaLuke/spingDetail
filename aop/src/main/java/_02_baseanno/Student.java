package _02_baseanno;

import org.springframework.stereotype.Component;

@Component
public class Student {
    private String name;

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }
}
