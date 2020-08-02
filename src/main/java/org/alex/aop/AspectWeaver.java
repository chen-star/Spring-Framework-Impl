package org.alex.aop;

import org.alex.aop.annotation.Aspect;
import org.alex.aop.annotation.Order;
import org.alex.aop.aspect.AspectInfo;
import org.alex.aop.aspect.DefaultAspect;
import org.alex.core.BeanContainer;
import org.alex.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 10:38
 */
public class AspectWeaver {

    private BeanContainer beanContainer;

    public AspectWeaver() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        // 1. Get all Aspects
        Set<Class<?>> aspectSet = beanContainer.getClassByAnnotation(Aspect.class);
        if (ValidationUtil.isEmpty(aspectSet)) {
            return;
        }

        // 2. Get AspectInfoList
        List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);

        // 3. Get all beans in the container
        Set<Class<?>> classSet = beanContainer.getClasses();
        for (Class<?> targetClass : classSet) {
            if (targetClass.isAnnotationPresent(Aspect.class)) {
                continue;
            }
            List<AspectInfo> roughMatchedAspectList = collectRoughMatchedAspectListForSpecificClass(aspectInfoList, targetClass);

            // 4. Weave
            wrapIfNecessary(roughMatchedAspectList, targetClass);
        }
    }

    private void wrapIfNecessary(List<AspectInfo> roughMatchedAspectList, Class<?> targetClass) {
        if (ValidationUtil.isEmpty(roughMatchedAspectList)) {
            return;
        }

        // init dynamic proxy
        AspectListExecutor executor = new AspectListExecutor(targetClass, roughMatchedAspectList);
        Object proxyBean = ProxyCreator.createProxy(targetClass, executor);
        beanContainer.addBean(targetClass, proxyBean);
    }

    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(List<AspectInfo> aspectInfoList, Class<?> targetClass) {
        List<AspectInfo> roughMatchedAspectList = new ArrayList<>();
        for (AspectInfo aspectInfo : aspectInfoList) {
            if (aspectInfo.getPointcutLocator().roughMatches(targetClass)) {
                roughMatchedAspectList.add(aspectInfo);
            }
        }
        return roughMatchedAspectList;
    }

    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for (Class<?> aspectClass : aspectSet) {
            if (verifyAspect(aspectClass)) {
                Order orderTag = aspectClass.getAnnotation(Order.class);
                Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
                DefaultAspect defaultAspect = (DefaultAspect) beanContainer.getBean(aspectClass);
                // init pointcut locator
                PointcutLocator pointcutLocator = new PointcutLocator(aspectTag.pointcut());
                AspectInfo aspectInfo = new AspectInfo(orderTag.value(), defaultAspect, pointcutLocator);
                aspectInfoList.add(aspectInfo);
            } else {
                throw new RuntimeException();
            }
        }
        return aspectInfoList;
    }

    /**
     * 1. Annotated with @Aspect & @Order
     * 2. extends DefaultAspect.class
     * 3. its value cannot be an Aspect
     */
    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class) &&
                aspectClass.isAnnotationPresent(Order.class) &&
                DefaultAspect.class.isAssignableFrom(aspectClass);
    }
}
