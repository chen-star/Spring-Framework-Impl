package org.alex.aop;

import org.alex.aop.annotation.Aspect;
import org.alex.aop.annotation.Order;
import org.alex.aop.aspect.AspectInfo;
import org.alex.aop.aspect.DefaultAspect;
import org.alex.core.BeanContainer;
import org.alex.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        // 2.key: Target class Annotation(eg. @Controller), value: AspectInfo list
        Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap = new HashMap<>();
        if (ValidationUtil.isEmpty(aspectSet)) {
            return;
        }
        for (Class<?> aspectClass : aspectSet) {
            if (verifyAspect(aspectClass)) {
                categorizeAspect(categorizedMap, aspectClass);
            } else {
                throw new RuntimeException("@Aspect & @Order must be present. Aspect class must extend DefaultAspect class. " +
                        "The value or @Aspect cannot be an Aspect");
            }
        }

        // 3. weave
        if (ValidationUtil.isEmpty(categorizedMap)) {
            return;
        }
        for (Class<? extends Annotation> category : categorizedMap.keySet()) {
            weaveByCategory(category, categorizedMap.get(category));
        }
    }

    private void weaveByCategory(Class<? extends Annotation> category, List<AspectInfo> aspectInfos) {
        // 1. get all proxyed objs
        Set<Class<?>> classSet = beanContainer.getClassByAnnotation(category);
        if (ValidationUtil.isEmpty(classSet)) {
            return;
        }

        // 2. create proxy for each proxyed obj
        for (Class<?> targetClass : classSet) {
            AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass, aspectInfos);
            Object proxyBean = ProxyCreator.createProxy(targetClass, aspectListExecutor);
            // 3. replace proxyed target class with proxyBean
            beanContainer.addBean(targetClass, proxyBean);
        }
    }

    /**
     * 1. Annotated with @Aspect & @Order
     * 2. extends DefaultAspect.class
     * 3. its value cannot be an Aspect
     */
    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class) &&
                aspectClass.isAnnotationPresent(Order.class) &&
                DefaultAspect.class.isAssignableFrom(aspectClass) &&
                aspectClass.getAnnotation(Aspect.class).value() != Aspect.class;
    }

    private void categorizeAspect(Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> aspectClass) {
        Order orderTag = aspectClass.getAnnotation(Order.class);
        Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
        DefaultAspect aspect = (DefaultAspect) beanContainer.getBean(aspectClass);
        AspectInfo aspectInfo = new AspectInfo(orderTag.value(), aspect);
        categorizedMap
                .computeIfAbsent(aspectTag.value(), l->new ArrayList<>())
                .add(aspectInfo);
    }
}
