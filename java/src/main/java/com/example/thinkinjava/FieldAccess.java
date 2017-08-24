package com.example.thinkinjava;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

/**
 * Java����������ķ��ʲ������ɱ�������������˲��Ƕ�̬�ġ�����������ͬ�����Զ�����䲻ͬ�Ĵ洢�ռ�
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
