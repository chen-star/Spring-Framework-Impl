package org.alex.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 23:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPathInfo {

    private String httpMethod;
    private String httpPath;
}
