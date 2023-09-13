# DI注解Autowired 与 Value

Autowired 与 Value 注解都是由Spring提供；Autowired 用来取代原有的xml配置文件中的bean的注入方式；Value 用来取代原有的xml配置文件中的bean的属性注入方式。

```xml
    <bean id="cat" class="_01_anno.Cat">
        <!-- 使用Value去取代传统bean注入属性方式 -->
    </bean>

    <bean id="person" class="_01_anno.Person">
    </bean>
```

```java
import org.springframework.beans.factory.annotation.Autowired;

class Cat {
    @Value("TomCat")
    private String name;
    // setter
}

class Person {
    private Cat cat;
    // setter
}

class Test1 {
    @Autowired
    private Person person;
    
    @Test
    public void test1() {
        // Person{cat=Cat{name='TomCat'}}
        System.out.println(person);
    }
}
```

## Autowired 注解使用

- 可以让Spring自动把属性或字段需要的对象找出来
- 可以贴在字段或者setter方法上
- 可同时注入多个对象
- 可注入一些 Spring 的内置对象
- 默认情况下 Autowired 注解需要找到对应的对象，如果找不到就会报错，可以通过设置 required 属性为 false 来避免错误
- Autowired 寻找bean的方式
- 1、 首先按照依赖对象的类型找，找到了就使用setter方法注入
- 2、 如果找到多个，就按照属性名和bean的id进行匹配，匹配上用setter注入
- 3、 可以使用 @Qualifier 注解来指定需要注入的bean的id，而不是使用属性名


## IOC注解

使用Spring中，如果对象过多，全部配置在xml中导致维护起来困难，如何简化

```xml
<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl"></bean>
<bean id="userService" class="com.itheima.service.impl.UserServiceImpl">
    <property name="userDao" ref="userDao"></property>
</bean>
<!-- more and more -->
```

**使用注解的方式**

- @Repository：用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean
- @Service：用于将业务层 (Service 层 ) 的类标识为 Spring Bean
- @Controller：用于将控制层 (Controller 层 ) 的类标识为 Spring Bean
- @Component：泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注

如想要将`Cat`和`Person`不使用xml的方式注入到Spring中

```java
@Component
class Cat {
    @Value("TomCat")
    private String name;
    // setter
}

@Component
class Person{
    @Autowired
    private Cat cat;
    // setter
}
```

@Component等价于`<bean id="cat" class="_01_anno.Cat"></bean>`

在使用注解时，需要在配置文件中配置注解扫描器，让其扫描指定的包，将注解的类注入到Spring中

```xml
<context:component-scan base-package="_01_anno"></context:component-scan>
```

扫描后，注入的bean对象才不会报错

## 控制事务

在传统业务场景中对事务进行控制，需要在业务层中进行控制，如下

```java
class OrderServiceImpl implement IOrderService{
    public void save(Order order){
        // 开启事务
        try{
            // 保存订单
            // 提交事务
        }catch(Exception e){
            // 回滚事务
        } finally {
            // 释放资源
        }
    }
}
```

在所有的业务中可能都存在以上事务操作，导致的问题

- 代码量过大，维护成本高
- 事务控制与业务逻辑混合在一起，没有做到责任分离


## 代理模式

- 代理对象完全包含真实对象，客户端使用的都是代理对象的方法，和真实对象没有直接关系
- 代理对象将不是真实对象该做的事情从真实对象上撇开---责任分离

**分类**

代理对象分为`静态代理`和`动态代理`

- 静态代理：代理对象和真实对象都是在编译期间确定下来的，使用时需要手动创建代理对象
- 动态代理：代理对象和真实对象都是在运行期间确定下来的，调用时在内存中生成字节码文件，加载到虚拟机中

**使用静态代理处理事务**

不使用代理下
```java
interface OrderService{
    void saveOrder();
}
class OrderServiceImpl implements OrderService{
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
```
现在业务代码与事务代码没有分离，管理起来可能困难

使用代理处理业务
```java
class OrderServiceImpl implements OrderService{
    @Override
    public void saveOrder() {
        System.out.println("保存");
    }
}
class OrderServiceProxy implements OrderService{
    // 保存被代理的对象
    private OrderServiceImpl target;
    
    public void setTarget(OrderServiceImpl os){
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

class Test{
    public void test1(){
        OrderService os = new OrderServiceImpl();
        OrderServiceProxy osp = new OrderServiceProxy();
        osp.setTarget(os);
        os.saveOrder();
    }
}
```

使用代理类管理事务
```java
class TransactionManage{
    public void begin(){}
    public void commit(){}
    public void rollback(){}
    public void release(){}
}
class OrderServiceProxy implements OrderService{
    // 保存被代理的对象
    private OrderServiceImpl target;

    public void setTarget(OrderServiceImpl os){
        this.target = os;
    }
    
    private TransactionManage tm;
    
    public void setTM(TransactionManage tm){
        this.tm = tm;
    }
    @Override
    public void saveOrder() {
        try{
            tm.begin();
            // 调用真实对象方法
            target.saveOrder();
            tm.commit();
        }catch (Exception e){
            e.printStackTrace();
            tm.rollback();
        } finally {
            tm.release();
        }
    }
}
```

## 动态代理

**JDK动态代理**

`1、java.lang.reflect.Proxy`

Java动态代理机制生成的所有动态代理类的父类，提供了一组静态方法来为一组接口动态生成代理类及其对象
`public static Obejct newProxyinstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler handler)`

- 为指定类加载器，一组接口及第哦啊用处理器生成动态代理类实例
- loader：类加载器，传递真实对象的类加载器；interfaces：代理类需要实现的接口；handler：代理执行处理器（代理对象需要干什么）

`2、java.lang.relect.InvocationHandler`

主要方法：`public Object invoke(Object proxy, Method method, Object[] args)`

- 负责几种处理动态代理类上的所有方法调用，让使用者自定义做什么事情，对原来方法的增强
- proxy：代理对象；method：调用的真实对象；args：调用方法的实参





























