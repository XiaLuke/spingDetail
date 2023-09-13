package _03_staticProxy.withProxy;

import _03_staticProxy.OrderService;

/**
 * @author XF
 * @date 2023/09/13
 * @description 代理类，代理对象
 */
public class OrderServiceImplProxy implements OrderService {

    // 存实际的业务对象
    private OrderService target;

    public void setTarget(OrderService os){
        this.target = os;
    }

    @Override
    public void saveOrder() {
        try{
            System.out.println("开启事务");
            // 调用真实对象方法
            target.saveOrder();
            System.out.println("提交事务");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("回滚事务");
        } finally {
            System.out.println("释放资源");
        }
    }
}

