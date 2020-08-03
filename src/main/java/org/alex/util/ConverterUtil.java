package org.alex.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ClassUtils;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-03 09:10
 */
public class ConverterUtil {


    public static Object primitiveNull(Class<?> type) {
        if (type == int.class || type == double.class || type == short.class ||
                type == long.class || type == byte.class || type == float.class) {
            return 0;
        } else if (type == boolean.class) {
            return false;
        }
        return null;
    }

    public static Object convert(Class<?> type, String requestValue) {
        if (isPrimitive(type)) {
            if (ValidationUtil.isEmpty(requestValue)) {
                return primitiveNull(type);
            }
            return ConvertUtils.convert(requestValue, type);
        } else {
            throw new RuntimeException("Not support non-primitive yet");
        }
    }

    private static boolean isPrimitive(Class<?> type) {
        return type == String.class
                || type.isPrimitive()
                || ClassUtils.wrapperToPrimitive(type) != null;
    }
}
