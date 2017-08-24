package com.orange.demo.annotation;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class Person {
    @Name("黄阳")
    private String name;

    @Gender(gender = Gender.GenderType.MALE)
    private Gender.GenderType gender;

    @Profile(height = 170)
    private int height;

    @Profile(nativePlace = "江西抚州")
    private String nativePlace;

    @Profile(id = 14)
    private int id;
}
