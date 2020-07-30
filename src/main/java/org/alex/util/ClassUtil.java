package org.alex.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-29 14:16
 */
@Slf4j
public class ClassUtil {

    public static final String FILE_PROTOCOL = "file";

    /**
     * Get all classes under the package passed in
     *
     * @param packageName package to extract classes from
     * @return all classes under the package
     */
    public static Set<Class<?>>  extractPackageClass(String packageName) {
        // 1. get class loader => get path of the package
        ClassLoader classLoader = getClassLoader();

        // 2. get URL via class loader
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            log.warn("unable to retrieve resource from package {}", packageName);
            return null;
        }

        // 3.get resources set
        Set<Class<?>> classSet = null;
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            classSet = new HashSet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }

        return classSet;
    }

    /**
     * Get class loader
     *
     * @return class loader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * Get classes under the package and its sub-packages
     *
     * @param classSet add classes to the classSet
     * @param fileSource file resource
     * @param packageName package name
     */
    private static void extractClassFile(Set<Class<?>> classSet, File fileSource, String packageName) {
        if (!fileSource.isDirectory()) {
            return;
        }
        
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    String absoluteFilePath = file.getAbsolutePath();
                    if (absoluteFilePath.endsWith(".class")) {
                        addToClassSet(absoluteFilePath);
                    }
                }
                return false;
            }

            private void addToClassSet(String absoluteFilePath) {
                absoluteFilePath = absoluteFilePath.replace(File.separator, ".");
                String className = absoluteFilePath.substring(absoluteFilePath.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));
                Class targetClass = loadClass(className);
                classSet.add(targetClass);
            }
        });

        if (files != null) {
            for (File f : files) {
                extractClassFile(classSet, f, packageName);
            }
        }
    }

    /**
     * Get class from class name
     *
     * @return class
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.warn("load class err {}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new instance of the given clazz
     * @param clazz class
     * @param accessible supports private class or not
     * @param <T> class instance
     * @return class instance
     */
    public static <T> T newInstance(Class<?> clazz, boolean accessible) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T) constructor.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            log.error("Cannot create a new instance for {}, {}", clazz, e);
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Set field in the target obj with value
     *
     * @param field field
     * @param target target obj
     * @param value value of the field
     * @param accessible supports private or not
     */
    public static void setField(Field field, Object target, Object value, boolean accessible) {
        field.setAccessible(accessible);
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            log.error("set Filed error", e);
            throw new RuntimeException(e);
        }
    }
}
