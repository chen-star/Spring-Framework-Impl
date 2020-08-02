package com.alex.aop.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 00:03
 */
public class CglibTest {

    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        ServiceImpl service = getInstance(proxy);
        service.add();
        System.out.println();
        service.update();
    }

    private static ServiceImpl getInstance(CglibProxy proxy) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ServiceImpl.class);
        enhancer.setCallback(proxy);
        ServiceImpl service = (ServiceImpl) enhancer.create();
        return service;
    }
}
