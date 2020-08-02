package com.alex.aop.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 00:01
 */
public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("------ Before ------");

        Object result = methodProxy.invokeSuper(o, args);

        System.out.println("------ After ------");
        return result;
    }
}
