package _04_dynamicProxy._01jdkDynamic;

import _04_dynamicProxy._01jdkDynamic.handler.TransactionInvocationHandler;
import _04_dynamicProxy._01jdkDynamic.impl.DepartmentServiceImpl;
import _04_dynamicProxy._01jdkDynamic.impl.EmployeeServiceImpl;
import _04_dynamicProxy._01jdkDynamic.tx.MyTransactionManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Proxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EmployeeServiceTest {

    @Autowired
    private TransactionInvocationHandler employeeTransactionInvocationHandler;

    @Test
    public void test() {
        // 生成类，并返回对象，存到 proxy 变量上
        // 1、这里真是生成类创建这个类对象吗？
        IEmployeeService proxy = (IEmployeeService) Proxy.newProxyInstance(
                this.getClass().getClassLoader(), // 类加载器
                employeeTransactionInvocationHandler.getTarget()
                        .getClass().getInterfaces(),// 生成类要实现的接口
                employeeTransactionInvocationHandler // 生成类对象能做什么事情（调用事务管理器的方法，调用真实对象的方法，给业务类的方法增加模拟的事务）
        );
        // 2、为什么调用生成类对象的方法，会执行到 invoke 方法中
        // 多态
        proxy.save("诸葛亮", "666");
    }

}