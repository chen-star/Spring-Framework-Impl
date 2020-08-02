package org.alex.aop.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 10:08
 */
@AllArgsConstructor
@Getter
public class AspectInfo {

    private int orderIndex;

    private DefaultAspect aspectObject;
}
