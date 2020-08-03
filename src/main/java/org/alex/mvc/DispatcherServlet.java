package org.alex.mvc;

import lombok.extern.slf4j.Slf4j;
import org.alex.aop.AspectWeaver;
import org.alex.core.BeanContainer;
import org.alex.inject.annotation.DependencyInjector;
import org.alex.mvc.processor.RequestProcessor;
import org.alex.mvc.processor.impl.ControllerRequestProcessor;
import org.alex.mvc.processor.impl.JspRequestProcessor;
import org.alex.mvc.processor.impl.PreRequestProcessor;
import org.alex.mvc.processor.impl.StaticResourceRequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 17:04
 */
@Slf4j
@WebServlet("/*") // intercept all requests
public class DispatcherServlet extends HttpServlet {

    List<RequestProcessor> PROCESSOR = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // 1. init container
        BeanContainer container = BeanContainer.getInstance();
        container.loadBeans("com.alex");
        new AspectWeaver().doAop();
        new DependencyInjector().doIOC();

        // 2. init processor chain
        PROCESSOR.add(new PreRequestProcessor());
        PROCESSOR.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSOR.add(new JspRequestProcessor(getServletContext()));
        PROCESSOR.add(new ControllerRequestProcessor(getServletContext()));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Request path is: {}, method is {}", req.getServletPath(), req.getMethod());
        // 1. init RequestProcessor Chain
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSOR.iterator(), req, resp);

        // 2. process request
        requestProcessorChain.doRequestProcessorChain();

        // 3. render response
        requestProcessorChain.doRender();
    }
}
