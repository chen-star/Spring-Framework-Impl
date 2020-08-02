package com.alex.aop.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-01 23:58
 */
public class JDKTest {

    public static void main(String[] args) {
        Service service = new ServiceImpl();
        JDKInvocationHandler handler = new JDKInvocationHandler(service);
        Service serviceProxy = (Service) Proxy.newProxyInstance(service.getClass().getClassLoader(),
                                            service.getClass().getInterfaces(), handler);
        serviceProxy.add();
        System.out.println();
        serviceProxy.update();
    }
}
