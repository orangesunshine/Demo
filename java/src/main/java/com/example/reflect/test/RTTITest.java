package com.example.reflect.test;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

interface HasBatteries {
}

interface WaterProof {
}

interface Shoots {
}

class Toy {
    Toy() {
    }

    Toy(int i) {
    }
}

class FancyToy extends Toy
        implements HasBatteries, WaterProof, Shoots {
    FancyToy() {
        super(1);
    }
}

public class RTTITest {

    static void printInfo(Class cc) {
        System.out.println("Class name: " + cc.getName() +
                ", is interface? [" + cc.isInterface() + "]");
        System.out.println("Simple name: " + cc.getSimpleName());
        System.out.println("Canonical name: " + cc.getCanonicalName());
    }

    public static void main(String[] args) {
        //�����Զ��س�ʼ����Class����, ��ʼ�����ӳٵ��˶Ծ�̬��������������ʽ���Ǿ�̬�ģ����߷�final��̬��ע��final��̬�򲻻ᴥ����ʼ��������,��ʹ��Class.forNameʱ���Զ��ĳ�ʼ����
        Class c = FancyToy.class;;
//        try {
//            c = Class.forName("com.example.reflect.test.FancyToy"); // ������ȫ�޶���������+������
//        } catch (ClassNotFoundException e) {
//            System.out.println("Can't find FancyToy");
//            System.exit(1);
//        }
        printInfo(c);

        for (Class face : c.getInterfaces()) {
            printInfo(face);
        }

        Class up = c.getSuperclass();
        printInfo(up);
        Object obj = null;
        try {
            // Requires default constructor.
            obj = up.newInstance();
        } catch (InstantiationException e) {
            System.out.println("Can't Instantiate");
            System.exit(1);
        } catch (IllegalAccessException e) {
            System.out.println("Can't access");
            System.exit(1);
        }
        printInfo(obj.getClass());
    }

}
