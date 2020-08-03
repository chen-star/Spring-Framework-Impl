package org.alex.mvc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.alex.mvc.processor.RequestProcessor;
import org.alex.mvc.render.ResultRender;
import org.alex.mvc.render.impl.DefaultResultRender;
import org.alex.mvc.render.impl.InternalErrorResultRender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 21:34
 */
@Slf4j
@Data
public class RequestProcessorChain {

    private Iterator<RequestProcessor> iterator;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String requestMethod;
    private String requestPath;
    private int responseCode;
    private ResultRender resultRender;

    public RequestProcessorChain(Iterator<RequestProcessor> iterator, HttpServletRequest req, HttpServletResponse resp) {
        this.iterator = iterator;
        this.request = req;
        this.response = resp;
        this.requestMethod = req.getMethod();
        this.requestPath = req.getPathInfo();
        this.responseCode = HttpServletResponse.SC_OK;
    }

    /**
     * Iterate each processor
     * until one of them return false
     * if exception is thrown, error render will handle the response
     */
    public void doRequestProcessorChain() {
        try {
            while (iterator.hasNext()) {
                RequestProcessor requestProcessor = iterator.next();
                if (!requestProcessor.process(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            this.resultRender = new InternalErrorResultRender();
            log.error("doRequestProcessorChain error: ", e);
        }
    }

    /**
     * 1. if no matched render, use default one
     * 2. render response
     */
    public void doRender() {
        if (this.resultRender == null) {
            this.resultRender = new DefaultResultRender();
        }
        try {
            this.resultRender.render(this);
        } catch (Exception e) {
            log.error("doRender, error: ", e);
            throw new RuntimeException(e);
        }
    }
}
