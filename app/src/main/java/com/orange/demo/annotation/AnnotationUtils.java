package com.orange.demo.annotation;

import com.orange.demo.utils.LogUtil;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class AnnotationUtils {

    public static void main(String[] arg) {
        getInfo(Person.class);
    }

    public static void getInfo(Class clazz) {
        String name = "";
        String gender = "";
        String profile = "";

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Name.class)) {
                Name annotation = declaredField.getAnnotation(Name.class);
                LogUtil.log("name: " + annotation.value());
            }

            if (declaredField.isAnnotationPresent(Gender.class)) {
                Gender annotation = declaredField.getAnnotation(Gender.class);
                LogUtil.log("gender: " + annotation.gender().toString());
            }

            if (declaredField.isAnnotationPresent(Profile.class)) {
                Profile annotation = declaredField.getAnnotation(Profile.class);
                LogUtil.log("profile: " + annotation.toString());
            }
        }
    }
}
