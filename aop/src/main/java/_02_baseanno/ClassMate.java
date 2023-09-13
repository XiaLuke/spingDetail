package _02_baseanno;

import org.springframework.stereotype.Component;

@Component
public class ClassMate {
    private Student student;

    public Student student() {
        return student;
    }

    public ClassMate setStudent(Student student) {
        this.student = student;
        return this;
    }
}
