package org.alex.mvc.render;

import org.alex.mvc.RequestProcessorChain;

/**
 * @author Jiaxin CHEN
 * @version 1.0
 * @since 2020-08-02-21-44
 */
public interface ResultRender {

    void render(RequestProcessorChain requestProcessorChain) throws Exception;
}
