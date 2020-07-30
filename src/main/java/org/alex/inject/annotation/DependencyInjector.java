package org.alex.inject.annotation;

import lombok.extern.slf4j.Slf4j;
import org.alex.core.BeanContainer;
import org.alex.util.ClassUtil;
import org.alex.util.ValidationUtil;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-29 23:05
 */
@Slf4j
public class DependencyInjector {

    private BeanContainer beanContainer;

    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doIOC() {
        // 1. get all classes in the container
        if (ValidationUtil.isEmpty(beanContainer.getClasses())) {
            log.warn("empty classSet in Bean Container");
            return;
        }
        for (Class<?> clazz : beanContainer.getClasses()) {
            // 2. get all fields in this clazz
            Field[] fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)) {
                continue;
            }
            for (Field field : fields) {
                // 3. check if this field has @Autowired
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autowiredValue = autowired.value();
                    // 4. get the class type of this field
                    Class<?> fieldClass = field.getType();
                    // 5. get instance of this class
                    Object fieldValue = getFieldInstance(fieldClass, autowiredValue);
                    if (fieldValue == null) {
                        throw new RuntimeException(fieldClass + " is null");
                    } else {
                        // 6. use reflection to inject filed instance
                        Object targetBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(field, targetBean, fieldValue, true);
                    }
                }
            }
        }
    }

    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        Object fieldValue = beanContainer.getBean(fieldClass);
        if (fieldValue != null) {
            return fieldValue;
        } else {
            Class<?> implementedClass = getImplementClass(fieldClass, autowiredValue);
            if (implementedClass != null) {
                return beanContainer.getBean(implementedClass);
            } else {
                return null;
            }
        }
    }

    private Class<?> getImplementClass(Class<?> fieldClass, String autowiredValue) {
        Set<Class<?>> classSet = beanContainer.getClassBySuper(fieldClass);
        if (!ValidationUtil.isEmpty(classSet)) {
            if (ValidationUtil.isEmpty(autowiredValue)) {
                if (classSet.size() == 1) {
                    return classSet.iterator().next();
                } else {
                    throw new RuntimeException("Two or more classes implemented classes for " + fieldClass.getName());
                }
            } else {
                Set<Class<?>> targetClass = classSet.stream()
                        .filter(clazz -> autowiredValue.endsWith(clazz.getSimpleName()))
                        .collect(Collectors.toSet());
                return targetClass.size() == 1 ? targetClass.iterator().next() : null;
            }
        }
        return null;
    }
}
