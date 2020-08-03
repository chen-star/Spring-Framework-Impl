package org.alex.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.alex.core.BeanContainer;
import org.alex.mvc.RequestProcessorChain;
import org.alex.mvc.annotation.RequestMapping;
import org.alex.mvc.annotation.RequestParam;
import org.alex.mvc.processor.RequestProcessor;
import org.alex.mvc.type.ControllerMethod;
import org.alex.mvc.type.RequestPathInfo;
import org.alex.util.ValidationUtil;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 21:34
 */
@Slf4j
public class ControllerRequestProcessor implements RequestProcessor {

    // IOC Container
    private BeanContainer beanContainer;

    private Map<RequestPathInfo, ControllerMethod> pathInfoControllerMethodMap = new ConcurrentHashMap<>();

    public ControllerRequestProcessor(ServletContext servletContext) {
        this.beanContainer = BeanContainer.getInstance();
        Set<Class<?>> requestMappingSet = beanContainer.getClassByAnnotation(RequestMapping.class);
        initPathControllerMethodMap(requestMappingSet);
    }

    private void initPathControllerMethodMap(Set<Class<?>> requestMappingSet) {
        if (ValidationUtil.isEmpty(requestMappingSet)) {
            return;
        }

        for (Class<?> requestMappingClazz : requestMappingSet) {
            // 1. get base path from @RequestMapping at controller class level
            RequestMapping requestMapping = requestMappingClazz.getAnnotation(RequestMapping.class);
            String basePath = requestMapping.value();
            if (!basePath.startsWith("/")) {
                basePath = "/" + basePath;
            }

            // 2. get remaining path from @RequestMapping at controller method level
            Method[] methods = requestMappingClazz.getDeclaredMethods();
            if (ValidationUtil.isEmpty(methods)) {
                continue;
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping methodRequest = method.getAnnotation(RequestMapping.class);
                    String methodPath = methodRequest.value();
                    if (!methodPath.startsWith("/")) {
                        methodPath = "/" + basePath;
                    }
                    String url = basePath + methodPath;

                    // 3. get params from @RequestParam
                    Map<String, Class<?>> methodParams = new HashMap<>();
                    Parameter[] parameters = method.getParameters();
                    if (ValidationUtil.isEmpty(parameters)) {
                        continue;
                    }
                    for (Parameter parameter : parameters) {
                        RequestParam param = parameter.getAnnotation(RequestParam.class);
                        if (param == null) {
                            throw new RuntimeException("The parameter must have @RequestParam");
                        }
                        methodParams.put(param.value(), parameter.getType());

                        // 4. Wrap as RequestPathInfo & ControllerMethod
                        String httpMethod = String.valueOf(requestMapping.method());
                        RequestPathInfo requestPathInfo = new RequestPathInfo(httpMethod, url);
                        ControllerMethod controllerMethod = new ControllerMethod(requestMappingClazz, method, methodParams);
                        this.pathInfoControllerMethodMap.put(requestPathInfo, controllerMethod);
                    }
                }
            }
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        return false;
    }
}
