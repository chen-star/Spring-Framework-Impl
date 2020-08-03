package org.alex.core;

import org.alex.mvc.DispatcherServlet;
import com.alex.controller.frontend.MainPageController;
import com.alex.service.solo.HeadLineService;
import com.alex.service.solo.impl.HeadLineServiceImpl;
import org.alex.core.annotation.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeanContainerTest {

    private static BeanContainer beanContainer;

    @BeforeAll
    static void init() {
        beanContainer = BeanContainer.getInstance();
    }

    @DisplayName("BeanContainerTest: loadBeansTest")
    @Order(1)
    @Test
    public void loadBeansTest() {
        Assertions.assertFalse(beanContainer.isLoaded());
        beanContainer.loadBeans("com.alex");
        Assertions.assertEquals(6, beanContainer.size());
        Assertions.assertTrue(beanContainer.isLoaded());
    }

    @DisplayName("BeanContainerTest: getBeansTest")
    @Order(2)
    @Test
    public void getBeansTest() {
        MainPageController controller = (MainPageController) beanContainer.getBean(MainPageController.class);
        Assertions.assertTrue(controller instanceof MainPageController);
        DispatcherServlet dispatcherServlet = (DispatcherServlet) beanContainer.getBean(DispatcherServlet.class);
        Assertions.assertNull(dispatcherServlet);
    }

    @DisplayName("BeanContainerTest: getClassesByAnnotationTest")
    @Order(3)
    @Test
    public void getClassesByAnnotationTest() {
        Assertions.assertTrue(beanContainer.isLoaded());
        Assertions.assertEquals(3, beanContainer.getClassByAnnotation(Controller.class).size());
    }

    @DisplayName("BeanContainerTest: getClassesBySuperTest")
    @Order(4)
    @Test
    public void getClassesBySuperTest() {
        Assertions.assertTrue(beanContainer.isLoaded());
        Assertions.assertTrue( beanContainer.getClassBySuper(HeadLineService.class).contains(HeadLineServiceImpl.class));
    }
}