package _01withNoSpring;

import org.junit.Test;

public class PersonTest {
    @Test
    public void test1(){
        Person p = new Person();
        p.setName("张三").setAge(18);
        System.out.println(p.run());
    }

}
