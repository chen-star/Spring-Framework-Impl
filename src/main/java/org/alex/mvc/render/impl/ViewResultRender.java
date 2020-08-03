package org.alex.mvc.render.impl;

import org.alex.mvc.RequestProcessorChain;
import org.alex.mvc.render.ResultRender;
import org.alex.mvc.type.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 21:45
 */
public class ViewResultRender implements ResultRender {

    private ModelAndView modelAndView;

    public ViewResultRender(Object mv) {
        if (mv instanceof ModelAndView) {
            this.modelAndView = (ModelAndView) mv;
        } else if (mv instanceof String) {
            this.modelAndView = new ModelAndView().setView((String) mv);
        } else {
            throw new RuntimeException("Illegal Request result type");
        }
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        // put attributes map into request
        HttpServletRequest request = requestProcessorChain.getRequest();
        HttpServletResponse response = requestProcessorChain.getResponse();
        String path = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModel();
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }

        request.getRequestDispatcher("/templates/" + path).forward(request, response);
    }
}
