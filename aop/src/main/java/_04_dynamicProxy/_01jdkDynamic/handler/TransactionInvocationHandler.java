package _04_dynamicProxy._01jdkDynamic.handler;


import _04_dynamicProxy._01jdkDynamic.tx.MyTransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TransactionInvocationHandler implements InvocationHandler {

    private MyTransactionManager tx;

    public void setTx(MyTransactionManager tx) {
        this.tx = tx;
    }

    // 存真实对象类定义 Object ，为了存任意类型的真实对象
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    // 自定义生成类的行为，具体能做什么事情
    // 目前调用事务管理器对象的方法，调用真实对象的方法
    // 3、这三个参数分别是什么
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object retValue = null;
        try {
            tx.begin();
            // 反射去调用真实对象的方法
            retValue = method.invoke(target, args);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        return retValue;
    }
}
