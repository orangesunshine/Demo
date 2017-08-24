package com.example.classload;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class ClassLoaderProTest {
    public static void main(String[] args) throws IOException {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader parent = systemClassLoader.getParent();
        ClassLoader parent1 = parent.getParent();
        System.out.println("systemClassLoader: "+systemClassLoader +", parent" + parent + ", parent1£º" + parent1);
    }
}
