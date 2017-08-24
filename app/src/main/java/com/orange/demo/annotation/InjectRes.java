package com.orange.demo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2016/12/10 0010.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRes {
    int value() default -1;
}
