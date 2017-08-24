package com.example.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class MyClassLoader extends ClassLoader {
    private String classDir;

    public MyClassLoader() {

    }

    public MyClassLoader(String classDir) {
        this.classDir = classDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPathFile = classDir + "\\" + name + ".class";
        System.out.println("classPathFile: " + classPathFile);
        try {
            FileInputStream fis = new FileInputStream(classPathFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            encodeAndDecode(fis, bos);
            byte[] bytes = bos.toByteArray();
            return defineClass(bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("main--args: " + Arrays.toString(args));
        String srcPath = args[0];
        String desPath = args[1];
        System.out.println("srcPath: " + srcPath);
        String desFileName = srcPath.substring(srcPath.lastIndexOf("\\") + 1);
        String desPathFile = desPath + "/" + desFileName;
        FileInputStream fis = new FileInputStream(srcPath);
        File file = new File(desPathFile);
        if (!file.exists())
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(desPathFile);
        encodeAndDecode(fis, fos);
        fos.close();
        fis.close();
    }

    /**
     * 加密和解密算法
     *
     * @param is
     * @param os
     * @throws Exception
     */
    private static void encodeAndDecode(InputStream is, OutputStream os) throws Exception {
        int bytes = -1;
        while ((bytes = is.read()) != -1) {
            bytes = bytes ^ 0xff;//和0xff进行异或处理  
            os.write(bytes);
        }
    }
}
