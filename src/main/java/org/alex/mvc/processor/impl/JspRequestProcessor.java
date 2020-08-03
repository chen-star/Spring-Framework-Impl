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
@Slf4j
public class JspRequestProcessor implements RequestProcessor {

    public static final String JSP_RESOURCE_PREFIX = "/templates/";
    private final String JSP_SERVLET = "jsp";
    RequestDispatcher jspDispatcher;


    public JspRequestProcessor(ServletContext servletContext) {
        this.jspDispatcher = servletContext.getNamedDispatcher(JSP_SERVLET);
        if (this.jspDispatcher == null) {
            throw new RuntimeException("There is no jsp servlet");
        }
        log.info("The default servlet for static resource is {}", JSP_SERVLET);
    }


    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        if (isJspResource(requestProcessorChain.getRequestPath())) {
            jspDispatcher.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    private boolean isJspResource(String requestPath) {
        return requestPath.startsWith(JSP_RESOURCE_PREFIX);
    }
}
