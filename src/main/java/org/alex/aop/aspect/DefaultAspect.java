package org.alex.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 09:57
 */
public class DefaultAspect {

    /**
     * Before Method
     *
     * @param targetClass proxyed class
     * @param method proxyed method
     * @param args method args
     * @throws Throwable
     */
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
    }

    /**
     * After returning value
     *
     * @param targetClass proxyed class
     * @param method proxyed method
     * @param args method args
     * @throws Throwable
     */
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        return returnValue;
    }

    /**
     * After method throwing exception
     *
     * @param targetClass proxyed class
     * @param method proxyed method
     * @param args method args
     * @throws Throwable
     */
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {
    }
}
