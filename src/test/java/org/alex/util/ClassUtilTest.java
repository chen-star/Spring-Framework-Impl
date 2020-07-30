package org.alex.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ClassUtilTest {

    @DisplayName("Extract class from given package Test: extractPackageClassTest")
    @Test
    public void extractPackageClassTest() {
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.alex.entity");
        System.out.println(classSet);
        Assertions.assertEquals(4, classSet.size());
    }

}