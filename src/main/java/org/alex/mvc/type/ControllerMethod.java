package org.alex.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 22:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerMethod {

    private Class<?> controllerClass;
    private Method invokeMethod;
    private Map<String, Class<?>> methodParams;
}
