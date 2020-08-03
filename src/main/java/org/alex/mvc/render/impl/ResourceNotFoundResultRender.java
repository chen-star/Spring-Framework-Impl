package org.alex.mvc.render.impl;

import org.alex.mvc.RequestProcessorChain;
import org.alex.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 21:45
 */
public class ResourceNotFoundResultRender implements ResultRender {

    String httpMethod;
    String httpPath;

    public ResourceNotFoundResultRender(String method, String path) {
        this.httpMethod = method;
        this.httpPath = path;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND,
                "Resource not found for method[" + httpMethod + "] url[" + httpPath + "]");
    }
}
