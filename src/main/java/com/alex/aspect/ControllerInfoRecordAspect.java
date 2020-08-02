package com.alex.aspect;

import lombok.extern.slf4j.Slf4j;
import org.alex.aop.annotation.Aspect;
import org.alex.aop.annotation.Order;
import org.alex.aop.aspect.DefaultAspect;
import org.alex.core.annotation.Controller;

import java.lang.reflect.Method;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 12:04
 */
@Slf4j
@Aspect(value = Controller.class)
@Order(value = 10)
public class ControllerInfoRecordAspect extends DefaultAspect {

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("[ControllerInfoRecordAspect] Start -- target class: [{}], method: [{}], args: [{}]", targetClass, method, args);
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        log.info("[ControllerInfoRecordAspect] End -- target class: [{}], method: [{}], args: [{}]", targetClass, method, args);
        return returnValue;
    }

    @Override
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {
        log.info("[ControllerInfoRecordAspect] Throwing -- target class: [{}], method: [{}], args: [{}], exception: [{}]", targetClass, method, args, e);
    }
}
