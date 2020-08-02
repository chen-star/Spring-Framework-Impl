package com.alex.aspect;

import lombok.extern.slf4j.Slf4j;
import org.alex.aop.annotation.Aspect;
import org.alex.aop.annotation.Order;
import org.alex.aop.aspect.DefaultAspect;

import java.lang.reflect.Method;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 11:42
 */
@Slf4j
@Aspect(pointcut = "within(org.alex.core.annotation.Component)")
@Order(value = 0)
public class ServiceTimeCalculatorAspect extends DefaultAspect {

    private long timestampCache;

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("[ServiceTimeCalculatorAspect] Start -- target class: [{}], method: [{}], args: [{}]", targetClass, method, args);
        timestampCache = System.currentTimeMillis();
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        long endTime = System.currentTimeMillis();
        long costTime = endTime - timestampCache;
        log.info("[ServiceTimeCalculatorAspect] End -- target class: [{}], method: [{}], args: [{}]", targetClass, method, args);
        log.info("[ServiceTimeCalculatorAspect] Time Spent: {}", costTime);
        return returnValue;
    }
}
