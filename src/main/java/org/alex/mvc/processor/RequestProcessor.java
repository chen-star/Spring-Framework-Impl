package org.alex.mvc.processor;

import org.alex.mvc.RequestProcessorChain;

/**
 * @author Jiaxin CHEN
 * @version 1.0
 * @since 2020-08-02-21-33
 */
public interface RequestProcessor {

    boolean process(RequestProcessorChain requestProcessorChain) throws Exception;
}
