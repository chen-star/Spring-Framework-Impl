package org.alex.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alex.aop.annotation.Aspect;
import org.alex.core.annotation.Component;
import org.alex.core.annotation.Controller;
import org.alex.core.annotation.Repository;
import org.alex.core.annotation.Service;
import org.alex.util.ClassUtil;
import org.alex.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-29 15:43
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION =
            Arrays.asList(Component.class, Controller.class, Service.class, Repository.class, Aspect.class);

    @Getter
    private boolean loaded = false;

    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    public synchronized void loadBeans(String packageName) {
        if (isLoaded()) {
            log.warn("Bean container is loaded");
            return;
        }

        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("nothing in the package {}", packageName);
            return;
        }

        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                if (clazz.isAnnotationPresent(annotation)) {
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }

        loaded = true;
    }

    /**
     * Add a bean into container
     *
     * @param clazz clazz
     * @param bean  bean instance
     * @return bean instance
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    /**
     * Remove a bean
     *
     * @param clazz bean class
     * @return bean instance
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * Get a bean
     *
     * @param clazz bean class
     * @return bean instance
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    /**
     * All classes
     *
     * @return all classes
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * All classes
     *
     * @return all classes
     */
    public Set<Object> getValues() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * All classes with given annotaiton
     *
     * @return all classes
     */
    public Set<Class<?>> getClassByAnnotation(Class<? extends Annotation> annotation) {
        Set<Class<?>> keySet = beanMap.keySet();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return null;
        }

        Set<Class<?>> classSet = keySet.stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
        return classSet.isEmpty() ? null : classSet;
    }

    /**
     * All classes with given super class or interface
     *
     * @return all classes
     */
    public Set<Class<?>> getClassBySuper(Class<?> interfaceOrClass) {
        Set<Class<?>> keySet = beanMap.keySet();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return null;
        }

        Set<Class<?>> classSet = keySet.stream()
                .filter(clazz -> interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass))
                .collect(Collectors.toSet());
        return classSet.isEmpty() ? null : classSet;
    }

    public int size() {
        return beanMap.size();
    }
}
