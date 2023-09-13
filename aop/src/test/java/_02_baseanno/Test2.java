package _02_baseanno;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:_02_baseanno/anno.xml")
public class Test2 {
    @Autowired
    private ClassMate classMate;
    @Autowired
    private Student student;

    @Test
    public void test1(){
        System.out.println(classMate);
        System.out.println(student);
    }
}
