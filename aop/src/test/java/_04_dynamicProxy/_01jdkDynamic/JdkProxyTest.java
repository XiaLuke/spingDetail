package _04_dynamicProxy._01jdkDynamic;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTest {

    @Test
    public void test() {
        // 生成类，并创建这个类型返回
        Object object = Proxy.newProxyInstance(
                this.getClass().getClassLoader(),// 类加载器
                new Class[]{},// 生成类要实现的接口
                new InvocationHandler() {
                    @Override
                    // 自定义生成类的对象的行为，就是可以做什么事情
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println(1 + 1);
                        return null;
                    }
                }// 生成类的对象具体能做什么事情
        );

        System.out.println(object); // 调用生成类的对象 toString 方法， 调用生成对象任意方法都会执行 invoke 方法
        System.out.println(object.hashCode()); // 调用生成类的对象 toString 方法， 调用生成对象任意方法都会执行 invoke 方法
    }

}
