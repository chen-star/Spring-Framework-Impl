package org.alex.aop;

import com.alex.controller.superadmin.HeadLineOperationController;
import org.alex.core.BeanContainer;
import org.alex.inject.annotation.DependencyInjector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AspectWeaverTest {

    @DisplayName("AspectWeaverTest: doAoPTest")
    @Test
    public void doAoPTest() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.alex");
        new AspectWeaver().doAop();
        new DependencyInjector().doIOC();
        HeadLineOperationController controller = (HeadLineOperationController) beanContainer.getBean(HeadLineOperationController.class);
        controller.addHeadLine(null, null);
    }

}