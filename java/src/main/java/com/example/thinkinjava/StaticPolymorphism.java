package com.example.thinkinjava;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

/**
 * static��final(private��������������final��������Ϊ���ܱ��������)�����⣬�����������Ƕ�̬��
 * final���������ɸ���Ч�Ĵ��룬һ���̶���������ܣ�Ч�������ԣ�
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
