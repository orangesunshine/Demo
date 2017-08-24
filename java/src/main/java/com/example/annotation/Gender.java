package com.example.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Gender {
    GenderType gender() default GenderType.MALE;

    public enum GenderType {
        MALE("ÄÐ"),
        FEMALE("Å®"),
        OTHER("ÆäËû");

        private String genderstr;

        private GenderType(String genderstr) {
            this.genderstr = genderstr;
        }

        @Override
        public String toString() {
            return "GenderType{" +
                    "genderstr='" + genderstr + '\'' +
                    '}';
        }
    }
}
