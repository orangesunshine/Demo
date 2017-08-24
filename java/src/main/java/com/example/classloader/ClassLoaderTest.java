package com.example.classloader;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class ClassLoaderTest {

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        //���ClassLoaderText�������������
        System.out.println("ClassLoaderText��ļ�����������:" + ClassLoaderTest.class.getClassLoader().getClass().getName());
        System.out.println("System��ļ�����������:" + System.class.getClassLoader());
        System.out.println("List��ļ�����������:" + List.class.getClassLoader());

        System.out.println("Ĭ�ϵ��������:" + ClassLoaderTest.class.getClassLoader().getSystemClassLoader());

        ClassLoader cl = ClassLoaderTest.class.getClassLoader();
        while (cl != null) {
            System.out.print(cl.getClass().getName() + "->");
            cl = cl.getParent();
        }
        System.out.println(cl);

        try {
            Class classDate = new MyClassLoader("C:\\Android\\Project\\Demo\\java\\src\\main\\java\\com\\example\\classloader\\class_temp").loadClass("MyDate");
            System.out.println("loadClass");
            Date date = (Date) classDate.newInstance();
            //���ClassLoaderAttachment��ļ���������
            System.out.println("ClassLoader:" + date.getClass().getClassLoader().getClass().getName());
            System.out.println("date: " + date);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


}
