package com.example.thinkinjava;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

/**
 * 为了使用类而做的准备工作实际包含三个步骤：
 - 加载：由类加载器执行。查找字节码，并从这些字节码中创建一个Class对象
 - 链接：验证类中的字节码，为静态域分配存储空间，并且如果必需的话，将解析这个类创建的对其他类的所有引用。
 - 初始化：如果该类具有超类，则对其初始化，执行静态初始化器和静态初始化块。
 */
public class InitTest {
    public static void main(String[] args){
//        Class<Test> testClass = Test.class;
        int age = Test.age;
//        try {
//            Class<?> aClass = Class.forName("com.example.thinkinjava.Test");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}

class Test{
    public final static int age = 15;
    static {
        System.out.println("static 代码块");
    }
    public Test() {
        System.out.println("Test--constuctor");
    }
}
