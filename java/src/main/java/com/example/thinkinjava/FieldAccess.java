package com.example.thinkinjava;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

/**
 * Java类中属性域的访问操作都由编译器解析，因此不是多态的。父类和子类的同名属性都会分配不同的存储空间
 */
// Direct field access is determined at compile time.
class Super {
    public int field = 0;
    public int getField() {
        return field;
    }
}

class Sub extends Super {
    public int field = 1;
    public int getField() {
        return field;
    }
    public int getSuperField() {
        return super.field;
    }
}

public class FieldAccess {

    public static void main(String[] args) {
        Super sup = new Sub();
        System.out.println("sup.filed = " + sup.field +
                ", sup.getField() = " + sup.getField());
        Sub sub = new Sub();
        System.out.println("sub.filed = " + sub.field +
                ", sub.getField() = " + sub.getField() +
                ", sub.getSuperField() = " + sub.getSuperField());
    }

}
