package org.alex.inject.annotation;

import com.alex.controller.frontend.MainPageController;
import com.alex.service.combine.impl.HeadLineShopCategoryCombineServiceImpl;
import com.alex.service.combine.impl.HeadLineShopCategoryCombineServiceImpl2;
import org.alex.core.BeanContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DependencyInjectorTest {

    @DisplayName("DependencyInjectorTest: doIocTest")
    @Test
    public void doIocTest() {
        BeanContainer container = BeanContainer.getInstance();
        container.loadBeans("com.alex");
        Assertions.assertTrue(container.isLoaded());

        MainPageController controller = (MainPageController) container.getBean(MainPageController.class);
        Assertions.assertTrue(controller instanceof MainPageController);

        Assertions.assertNull(controller.getHeadLineShopCategoryCombineService());
        new DependencyInjector().doIOC();
        Assertions.assertNotNull(controller.getHeadLineShopCategoryCombineService());
        Assertions.assertTrue(controller.getHeadLineShopCategoryCombineService() instanceof HeadLineShopCategoryCombineServiceImpl);
        Assertions.assertFalse(controller.getHeadLineShopCategoryCombineService() instanceof HeadLineShopCategoryCombineServiceImpl2);
    }
}