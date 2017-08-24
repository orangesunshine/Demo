
package com.example.reflect.test;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class InitialTest {
    public static void main(String[] args){
//        System.out.print(MyTest.compileConstant);
//        System.out.println("compileConstantTime");
//        System.out.print(MyTest.compileConstantTime);

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        try {
            systemClassLoader.loadClass("com.example.reflect.test.MyTest");
            System.out.println("系统加载MyTest类");
            Class<?> myTest = Class.forName("com.example.reflect.test.MyTest");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class MyTest{
    //编译时就确定了
//    public static final String compileConstant = "Crazy JAVA";
//    public static final long compileConstantTime = System.currentTimeMillis();
    static {
        System.out.println("静态初始化块……");
    }
}
