package _03_staticProxy;

import _03_staticProxy.withNoProxy.OrderServiceImpl;
import _03_staticProxy.withProxy.OrderServiceImplProxy;
import org.junit.Test;

public class Test3 {
    @Test
    public void test1(){
        OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.saveOrder();
    }

    @Test
    public void test2(){
        // 创建真实对象
        OrderService orderService = new OrderServiceImpl();
        // 创建代理对象
        OrderServiceImplProxy orderProxy = new OrderServiceImplProxy();

        orderProxy.setTarget(orderService);

        orderService.saveOrder();

    }
}
