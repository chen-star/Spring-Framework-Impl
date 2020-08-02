package com.alex.aop.proxy.cglib;

import com.alex.aop.proxy.jdk.Service;
import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-01 23:53
 */
public class ServiceImpl implements Service {

    @Getter
    public void add() {
        System.out.println("I'm Add Method!");
    }

    @Override
    public void update() {
        System.out.println("I'm Update Method!");
    }
}
