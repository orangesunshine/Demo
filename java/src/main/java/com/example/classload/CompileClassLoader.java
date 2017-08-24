package com.example.classload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class CompileClassLoader extends ClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (args.length < 1) {
            System.out.println("缺少目标类，请按如下格式运行java源文件： ");
            System.out.println("java CompileClassLoder className");
        }

        String progClass = args[0];
        String[] progArgs = new String[args.length - 1];
        System.arraycopy(args, 1, progArgs, 0, progArgs.length);
        CompileClassLoader compileClassLoader = new CompileClassLoader();
        Class<?> clazz = compileClassLoader.loadClass(progClass);
        Method main = clazz.getMethod("main", (new String[0]).getClass());
        Object argsArray[] = {progArgs};
        main.invoke(null,argsArray);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = null;
        String fileStub = name.replace(".", "/");
        String javaFilename = fileStub + ".java";
        String classFilename = fileStub + ".class";
        File javaFile = new File(javaFilename);
        File classFile = new File(classFilename);
        if (!javaFile.exists() && (!classFile.exists() || javaFile.lastModified() > classFile.lastModified())) {
            try {
                if (!compile(javaFilename) || !classFile.exists())
                    throw new ClassNotFoundException("ClassNotFoundException: " + javaFilename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (classFile.exists()) {
            byte[] bytes = getBytes(classFilename);
            clazz = defineClass(name, bytes, 0, bytes.length);
        }

        if (null != clazz)
            throw new ClassNotFoundException(name);

        return clazz;
    }

    private byte[] getBytes(String filename) {
        File file = new File(filename);
        long length = file.length();
        byte[] bytes = new byte[(int) length];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int read = fileInputStream.read(bytes);
            if (read != length) throw new IOException("无法读取全部文件: " + read + " != " + length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private boolean compile(String javaFile) throws IOException {
        System.out.println("正在编译 " + javaFile + "...");
        Process exec = Runtime.getRuntime().exec("javac " + javaFile);
        try {
            exec.waitFor();

        } catch (InterruptedException e) {
            System.out.println(e);
        }
        int ret = exec.exitValue();
        return ret == 0;
    }
}
