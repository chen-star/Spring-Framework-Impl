package org.alex.mvc.annotation;

import org.alex.mvc.type.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    // request path
    String value() default "";

    // request method
    RequestMethod method() default RequestMethod.GET;
}
