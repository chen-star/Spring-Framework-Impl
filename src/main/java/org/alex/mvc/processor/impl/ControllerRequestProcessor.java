package org.alex.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.alex.core.BeanContainer;
import org.alex.mvc.RequestProcessorChain;
import org.alex.mvc.annotation.RequestMapping;
import org.alex.mvc.annotation.RequestParam;
import org.alex.mvc.annotation.ResponseBody;
import org.alex.mvc.processor.RequestProcessor;
import org.alex.mvc.render.ResultRender;
import org.alex.mvc.render.impl.JsonResultRender;
import org.alex.mvc.render.impl.ResourceNotFoundResultRender;
import org.alex.mvc.render.impl.ViewResultRender;
import org.alex.mvc.type.ControllerMethod;
import org.alex.mvc.type.RequestPathInfo;
import org.alex.util.ConverterUtil;
import org.alex.util.ValidationUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        // 1. Get ControllerMethod by req uri and req method
        String method = requestProcessorChain.getRequestMethod();
        String path = requestProcessorChain.getRequestPath();
        ControllerMethod controllerMethod = this.pathInfoControllerMethodMap.get(new RequestPathInfo(method, path));
        if (controllerMethod == null) {
            requestProcessorChain.setResultRender(new ResourceNotFoundResultRender(method, path));
            return false;
        }

        // 2. Parse req param and invoke method in controller
        Object result = invokeControllerMethod(controllerMethod, requestProcessorChain.getRequest());

        // 3. delegate to render for response handling
        setResultRender(result, controllerMethod, requestProcessorChain);

        return false;
    }

    private Object invokeControllerMethod(ControllerMethod controllerMethod, HttpServletRequest request) {
        // 1. param map
        Map<String, String> requestParamMap = new HashMap<>();
        Map<String, String[]> paramMap = request.getParameterMap();
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
            if (!ValidationUtil.isEmpty(param.getValue())) {
                requestParamMap.put(param.getKey(), param.getValue()[0]);
            }
        }

        // 2. Parse request params to match with method arg type
        List<Object> methodParams = new ArrayList<>();
        Map<String, Class<?>> methodParamMap = controllerMethod.getMethodParams();
        for (String paramName : methodParamMap.keySet()) {
            Class<?> type = methodParamMap.get(paramName);
            String requestValue = requestParamMap.get(paramName);
            Object value;
            if (requestValue == null) {
                value = ConverterUtil.primitiveNull(type);
            } else {
                value = ConverterUtil.convert(type, requestValue);
            }
            methodParams.add(value);
        }

        // 3. invoke the method in the controller
        Object controller = beanContainer.getBean(controllerMethod.getControllerClass());
        Method methodToInvoke = controllerMethod.getInvokeMethod();
        // in case the method is private
        methodToInvoke.setAccessible(true);
        Object result;
        try {

            if (methodParams.isEmpty()) {
                result = methodToInvoke.invoke(controller);
            } else {
                result = methodToInvoke.invoke(controller, methodParams.toArray());
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private void setResultRender(Object result, ControllerMethod controllerMethod,
                                 RequestProcessorChain requestProcessorChain) {
        if (result == null) {
            return;
        }

        ResultRender resultRender;
        boolean isJson;
        if (isJson = controllerMethod.getInvokeMethod().isAnnotationPresent(ResponseBody.class)) {
            resultRender = new JsonResultRender(result);
        } else {
            resultRender = new ViewResultRender(result);
        }

        requestProcessorChain.setResultRender(resultRender);
    }
}
