package com.example.javapoet;

import com.squareup.javapoet.MethodSpec;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class Poet {
    public static void main(String[] args){
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addCode(""
                        + "int total = 0;\n"
                        + "for (int i = 0; i < 10; i++) {\n"
                        + "  total += i;\n"
                        + "}\n")
                .build();
    }
}
