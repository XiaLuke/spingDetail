package _02withSpring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WithSpringTest {
    @Test
    public void test1(){
        // 1. 读取配置文件
        ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:withSpringCreate.xml");
        // 2. 读取配置文件中设置的对象值
        Object obj = classPathXmlApplicationContext.getBean("student");
        Student student = (Student) obj;
        System.out.println(student.say());
    }
}
