package _02withSpring;

import _02withSpring.factory.Cat;
import _02withSpring.factory.UseLowFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:withSpringCreate.xml")
public class SpiringTest {

    @Autowired
    private ClassMate mate;

    @Autowired
    private Cat cat;

    @Test
    public void test1(){
        System.out.println(mate);
        System.out.println(mate.student());
    }

    @Test
    public void test2(){
        System.out.println(cat);
    }
}
