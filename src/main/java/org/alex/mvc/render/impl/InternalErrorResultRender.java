package org.alex.mvc.render.impl;

import org.alex.mvc.RequestProcessorChain;
import org.alex.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 21:45
 */
public class InternalErrorResultRender implements ResultRender {

    private String errorMsg;

    public InternalErrorResultRender(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMsg);
    }
}
