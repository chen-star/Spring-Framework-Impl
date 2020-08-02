package com.alex.aop.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-01 23:56
 */
public class JDKInvocationHandler implements InvocationHandler {

    private Object target;

    JDKInvocationHandler() {
        super();
    }

    JDKInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("------ Before ------");

        Object result = method.invoke(target, args);

        System.out.println("------ After ------");
        return result;
    }
}
