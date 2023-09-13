package _03_staticProxy.withProxy;

import _03_staticProxy.OrderService;

/**
 * @author XF
 * @date 2023/09/13
 */

public class OrderServiceImpl implements OrderService {
    @Override
    public void saveOrder() {
        System.out.println("save order");
    }
}

