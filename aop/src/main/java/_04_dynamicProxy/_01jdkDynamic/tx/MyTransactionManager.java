package _04_dynamicProxy._01jdkDynamic.tx;

// 里面封装事务相关的操作
public class MyTransactionManager {
    public void begin() {
        System.out.println("开启事务1");
        System.out.println("开启事务2");
        System.out.println("开启事务3");
    }

    public void commit(){
        System.out.println("提交事务1");
        System.out.println("提交事务2");

    }

    public void rollback() {
        System.out.println("回滚事务1");
        System.out.println("回滚事务2");
    }
}
