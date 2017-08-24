package com.example.thread;

/**
 * Created by Administrator on 2017/7/30 0030.
 */

public class WaitDemo {
    static Object o1 = new Object();
    static Object o2 = new Object();
    static boolean flag;

    public static void main(String[] args) {
        new C().start();
        new A().start();
        new B().start();
    }

    static class C extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "run");
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (flag) {
                    synchronized (o2) {
                        o2.notifyAll();
                    }
                } else {
                    synchronized (o1) {
                        o1.notifyAll();
                    }
                }
                flag = !flag;
            }
        }
    }

    static class A extends Thread {
        int i = 0;

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + "run");
                synchronized (o1) {
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println(Thread.currentThread().getName() + "--i: " + i * 2);
                    i++;
                    try {
                        o1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class B extends Thread {
        int i = 0;

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + "run");
                synchronized (o2) {
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println(Thread.currentThread().getName() + "--i: " + (i * 2 + 1));
                    i++;
                    try {
                        o2.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
