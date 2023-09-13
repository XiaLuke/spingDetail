package _01_anno;

public class Person {

    private Cat cat;

    public Person setCat(Cat cat) {
        this.cat = cat;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "cat=" + cat +
                '}';
    }
}
