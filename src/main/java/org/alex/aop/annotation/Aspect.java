package org.alex.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // class level
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    String pointcut();
}
