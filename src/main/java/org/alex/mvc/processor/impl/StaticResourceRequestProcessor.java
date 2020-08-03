package org.alex.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.alex.mvc.RequestProcessorChain;
import org.alex.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 21:34
 */

/**
 * Handle request asking for static resources under webapp/static/
 */
@Slf4j
public class StaticResourceRequestProcessor implements RequestProcessor {

    public static final String STATIC_RESOURCE_PREFIX = "/static/";
    private final String DEFAULT_TOMCAT_SERVLET = "default";
    RequestDispatcher defaultDispatcher;


    public StaticResourceRequestProcessor(ServletContext servletContext) {
        this.defaultDispatcher = servletContext.getNamedDispatcher(DEFAULT_TOMCAT_SERVLET);
        if (this.defaultDispatcher == null) {
            throw new RuntimeException("There is no default tomcat servlet");
        }
        log.info("The default servlet for static resource is {}", DEFAULT_TOMCAT_SERVLET);
    }


    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        if (isStaticResource(requestProcessorChain.getRequestPath())) {
            defaultDispatcher.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    private boolean isStaticResource(String requestPath) {
        return requestPath.startsWith(STATIC_RESOURCE_PREFIX);
    }
}
