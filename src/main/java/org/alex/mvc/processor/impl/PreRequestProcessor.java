package org.alex.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.alex.mvc.RequestProcessorChain;
import org.alex.mvc.processor.RequestProcessor;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 21:34
 */
@Slf4j
public class PreRequestProcessor implements RequestProcessor {

    private final String UTF8_ENCODING = "UTF-8";

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        // 1. encoding as UTF-8
        requestProcessorChain.getRequest().setCharacterEncoding(UTF8_ENCODING);

        // 2. remove tailing `/`
        String requestPath = requestProcessorChain.getRequestPath();
        if (requestPath.length() > 1 && requestPath.endsWith("/")) {
            requestProcessorChain.setRequestPath(requestPath.substring(0, requestPath.length() - 1));
        }

        log.info("[PreRequestProcessor] Request method: [{}], Request Path: [{}]",
                requestProcessorChain.getRequestMethod(), requestProcessorChain.getRequestPath());
        return true;
    }
}
