package com.example.thinkinjava;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

/**
 * Ϊ��ʹ���������׼������ʵ�ʰ����������裺
 - ���أ����������ִ�С������ֽ��룬������Щ�ֽ����д���һ��Class����
 - ���ӣ���֤���е��ֽ��룬Ϊ��̬�����洢�ռ䣬�����������Ļ�������������ഴ���Ķ���������������á�
 - ��ʼ�������������г��࣬������ʼ����ִ�о�̬��ʼ�����;�̬��ʼ���顣
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
        System.out.println("static �����");
    }
    public Test() {
        System.out.println("Test--constuctor");
    }
}
