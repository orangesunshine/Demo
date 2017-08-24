package com.example.classloader;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class MyDate extends Date {
    private static final long serialVersionUID = 7523967970024938905L;

    @Override
    public String toString() {
        return "MyDate";
    }
}
