package org.alex.mvc.processor.impl;

import org.alex.mvc.RequestProcessorChain;
import org.alex.mvc.processor.RequestProcessor;

import javax.servlet.ServletContext;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 21:34
 */
public class ControllerRequestProcessor implements RequestProcessor {

    public ControllerRequestProcessor(ServletContext servletContext) {

    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        return false;
    }
}
