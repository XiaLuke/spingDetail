## IOC和DI思想

ioc：完成对象的创建

di：在ioc容器创建对象的过程中，将对象的依赖属性通过配置设置给对象

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


