package _04_dynamicProxy._01jdkDynamic.impl;

import _04_dynamicProxy._01jdkDynamic.IEmployeeService;

// 真实类，真实对象，房东
public class EmployeeServiceImpl implements IEmployeeService {
    @Override
    public void save(String username, String password) {
        // 责任分离，符合单一原则
        // 业务操作，模拟
        System.out.println("保存了" + username + " : " + password);
    }
}
