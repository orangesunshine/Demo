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
        //不会自动地初始化该Class对象, 初始化被延迟到了对静态方法（构造器隐式的是静态的）或者非final静态域（注意final静态域不会触发初始化操作）,而使用Class.forName时会自动的初始化。
        Class c = FancyToy.class;;
//        try {
//            c = Class.forName("com.example.reflect.test.FancyToy"); // 必须是全限定名（包名+类名）
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
