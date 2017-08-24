package com.example.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class Test {
    public static void main(String[] args) {
        final List list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        final Thread a = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    list.add("d");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread b = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    list.add("e");
                    a.start();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

class B {
    public int age;
}

class A {
    void test() throws ArithmeticException {
        System.out.println("test--start");
        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException");
        }
        System.out.println("test--end");
        int random = new Random().nextInt(100);
        System.out.println("random: " + random);
        b(random);
    }

    void b(int i) {
        System.out.println("add: " + ++i);
    }
}
