package com.example.annotation;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class Person {
    @Name("»ÆÑô")
    private String name;

    @Gender(gender = Gender.GenderType.MALE)
    private Gender.GenderType gender;

    @Profile(id = 14, nativePlace = "½­Î÷¸§ÖÝ", height = 170)
    private String profile;
}
