package org.alex.aop;

import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.alex.aop.aspect.AspectInfo;
import org.alex.util.ValidationUtil;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 10:06
 */
public class AspectListExecutor implements MethodInterceptor {

    private Class<?> targetClass;

    @Getter
    private List<AspectInfo> sortedAspectInfoList;

    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList) {
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sortAspectInfoList(aspectInfoList);
    }

    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, Comparator.comparingInt(AspectInfo::getOrderIndex));
        return aspectInfoList;
    }

    /**
     * 1. ascending order => before
     * 2. method execution
     * 3. descending order => if returning value, afterReturning
     *                     => if throwing exception, afterThrowing
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
            return null;
        }

        // 1. before advices
        invokeBeforeAdvices(method, args);

        try {
            // 2. method execution
            returnValue = methodProxy.invokeSuper(proxy, args);

            // 3. afterReturning
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Exception e) {
            // 4. afterThrowing
            invokeAfterThrowingAdvices(method, args, e);
        }

        return returnValue;
    }

    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable{
        for (AspectInfo aspectInfo : sortedAspectInfoList) {
            aspectInfo.getAspectObject().before(targetClass, method, args);
        }
    }

    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable{
        Object result = null;
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            AspectInfo aspectInfo = sortedAspectInfoList.get(i);
            result = aspectInfo.getAspectObject().afterReturning(targetClass, method, args, returnValue);
        }
        return result;
    }

    private void invokeAfterThrowingAdvices(Method method, Object[] args, Exception e) throws Throwable{
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            AspectInfo aspectInfo = sortedAspectInfoList.get(i);
            aspectInfo.getAspectObject().afterThrowing(targetClass, method, args, e);
        }
    }
}
