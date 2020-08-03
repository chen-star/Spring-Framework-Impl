package org.alex.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 22:56
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {

    // request param value
    String value() default "";

    // if required
    boolean required() default true;
}
