package org.alex.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 23:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RequestPathInfo {

    @EqualsAndHashCode.Include
    private String httpMethod;

    @EqualsAndHashCode.Include
    private String httpPath;
}
