package com.example.thinkinjava;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

/**
 * static、final(private方法本质上属于final方法，因为不能被子类访问)方法外，其他方法都是动态绑定
 * final编译器生成更有效的代码，一定程度上提高性能（效果不明显）
 */
class StaticSuper {
    public static String staticGet() {
        return "Base staticGet()";
    }

    public String dynamicGet() {
        return "Base dynamicGet()";
    }
}

class StaticSub extends StaticSuper {
    public static String staticGet() {
        return "Derived staticGet()";
    }

    public String dynamicGet() {
        return "Derived dynamicGet()";
    }
}

public class StaticPolymorphism {

    public static void main(String[] args) {
        StaticSuper sup = new StaticSub();
        System.out.println(sup.staticGet());
        System.out.println(sup.dynamicGet());
    }

}
