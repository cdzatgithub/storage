package com.elephant.basic.storage.mongo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chendongzhi
 * @date 11:372018/8/28 0028
 * @description TODO(补充描述)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UpdateNull {
    boolean value() default true;
}
