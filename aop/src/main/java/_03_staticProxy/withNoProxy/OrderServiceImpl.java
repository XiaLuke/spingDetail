package _03_staticProxy.withNoProxy;

import _03_staticProxy.OrderService;

/**
 * @author XF
 * @date 2023/09/13
 * @description 模拟业务与事务责任不分离
 */

public class OrderServiceImpl implements OrderService {
    @Override
    public void saveOrder(){
        try {
            System.out.println("开启事务");
            System.out.println("save order");
            Thread.sleep(1000);
            System.out.println("提交事务");
        } catch (InterruptedException e) {
            System.out.println("回滚事务");
            e.printStackTrace();
        } finally {
            System.out.println("释放资源");
        }
    }
}

