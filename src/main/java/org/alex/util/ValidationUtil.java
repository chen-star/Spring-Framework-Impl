package org.alex.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-29 16:02
 */
public class ValidationUtil {

    public static boolean isEmpty(Collection<?> objects) {
        return objects == null || objects.isEmpty();
    }

    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    public static boolean isEmpty(String obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> objects) {
        return objects == null || objects.isEmpty();
    }
}
