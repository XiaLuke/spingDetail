# IOC和DI思想

ioc：完成对象的创建

di：在ioc容器创建对象的过程中，将对象的依赖属性通过配置设置给对象

# IOC

## Spring如何帮我们创建对象和设置属性值

** 1. 不使用Spring创建对象 **
  
```java
  class Student{
    private String name;

    public void say(){
      System.out.println(this.name+"say Hello");
    }

    // get / set
  }
  class Test1{
    @Test
    public void test1(){
      Student stu = new Student();
      stu.setName("sss");
      stu.say();
    }
  }
```

** 2. 使用Spring创建对象 **

想让Spring帮助我们创建对象，总要有相关依靠吧，Spring不会无缘无故创建对象

所以让Spring帮助我们创建对象有两种方式，通过配置文件、通过注解

方式一：通过配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--
        1. 告诉spring，通过以下配置创建Student对象
    -->
    <bean id="student" class="xxx.Student">
        <!-- 2. 设置参数 -->
        <property name="name" value="张三"/>
    </bean>
</beans>
```

有了配置文件，Spring怎么知道通过配置文件就可以创建对象了呢

```java
  class Test{
    @Test
    public void test1(){
      // 1. 读取配置文件
      ApplicationContext cls = new ClassPathXmlApplicationContext("classpath:配置文件地址");
      // 2. 通过配置文件中给对象设置的id值获取对象实例
      Student stu = (Student) cls.getBean("student");
      stu.say();
    }
  }
```

方式二：使用注解方式

```java
class Test{
  @AutoWrite
  private Student stu;

  @Test
  public void test1(){
    // 如果这里能打印出对象地址，即为正确
    System.out.println(stu);
  }
}
```

方式三：通过工厂类

```java
class FactoryCreaate {
  public Student createStu(){
    return new Student();
  }
}
```

方式四：通过配置文件工厂类和对象
```xml
  <bean id="studentFactory" class="xxx.Factory">

  <bean id="student" factory-bean="studentFactory" factory-method="createStu"/>
```

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:...xml")
class Test1{
  @Autowired
  private Student stu;

  @Test
  public void test1(){
    System.out.println(stu);
  }
}

```

方式四优化：

工厂类实现FactoryBean<>接口

```java
class FactoryCreaate implements FactoryBean<Student>}{
  @Override
  public Student getObject() throws Exception(){
    return new Student();
  }
  @Override
  public Class<?> getObjectType(){
    return Student.class; 
  }
}
```

```xml
<bean id="student" class="xxxx.FactoryCreaate">
```


## 针对以上得到的结论 **

** 1. ApplicationContext及bean

- BeanFactory：Spring底层接口，只提供了IOC的功能，负责创建、组装、管理bean
- ApplicationContext： 继承了BeanFactory，除此外提供了AOP的集成等功能

使用中一般使用ApplicationContext而不是BeanFactory

** 2. Spring配置方式 **

- xml配置方式
- 注解配置
- javabase

** 3. SpringIOC 管理Bean原理 **

- 使用反射创建对象，设置对象属性值
- 加载配置文件
- 解析配置文件，解析bean元素，id作为bean名字，class用于反射得到bean的实例，bean类碧血存在一个无参构造器
- 调用getBean方法的使用，从容器中返回对象实例

```java
class Test{
  public void tryTest() throws Exception{
    // 假设已从配置文件中解析到如下数据
    String className = "xxxx.Student";
    String objName = "student";
    String propertyName = "name";
    String propertyValue = "C罗";
    
    // 反射获取字节码对象
    Class<?> clzz = Class.forName(className);
    // 反射创建对象，对应类必须存在公共无参的构造函数
    Object obj = clzz.newInstance();
    
    BeanInfo beanInfo = Introspector.getBeanInfo(clzz, Object.class);
    for(PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
        // 判断属性名与解析 XML 中属性是否一致
        if(pd.getName().equals(propertyName)) {
            // 若一致，调用对应 setter 方法设置
            pd.getWriteMethod().invoke(obj, propertyValue);
        }
    }
    
    // 把对象存容器 省略
    
    ((Person) obj).doWork();
  }
}
```

## getBean方法

在上面使用Spring创建管理对象时，读取配置文件后从中获取的对象是`Object obj = cls.getBean("student")`,
这里返回的对象是Object，在进行对象强转的过程中，可能不太安全，所以提供以下方式

- 1. cls.getBean(Student.class), 这种方式可能会找到多个对象
- 2. cls.getBean("student", Student.class)，按照名字和类型进行匹配

## Bean的创建时机

通过给对象的无参构造器打印出任意话时，再在Spring读取配置文件前后打印相应的值可知，Bean在读取配置文件时就被创建

```java
class Test{
  public void test1(){
    // 读取配置文件后对象就被创建
    ApplicationContext cls = new ClassPathXmlApplicationContext("");  
  }
}
```

## Bean的作用域

主要包括，单例（singleton）、多例（prototype）、会话（session）、应用（application）、请求（request—）

## Bean的初始化和销毁

销毁：如DataSource，SqlSessionFactory最终都要关闭资源，手动调用close()，而交给Spring管理后，这些对象都会被Spring IOC容器管理，这些Bean使用完后都需要释放资源


初始化：当创建了部分对象的实例时，需要调用初始化方法，如DataSource，需要建立连接池

# DI

DI：指Spring创建对象的过程中，将对象依赖属性通过配置设置值给对象

## 注入方式

- setter
- 构造器初始化时设置

** 注入值 **

常见的注入值有常量、bean等

bean指向其中某个字段设置对象

```xml
<!-- 设置常量 -->
<bean id="student" class="xxx.Student">
  <!-- 设置属性中，虽然都是字符串，但是会隐式转换为对对应的属性 -->
  <property name="name" value="xxx"/>
</bean>

<!-- 注入bean -->
<bean id="classmate" class="xxx.ClassMate">
  <!-- ref 为容器中其他Bean的id值 -->
  <property name="student" ref="student"/>
</bean>
```
