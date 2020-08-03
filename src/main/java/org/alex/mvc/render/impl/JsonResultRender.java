package org.alex.mvc.render.impl;

import com.google.gson.Gson;
import org.alex.mvc.RequestProcessorChain;
import org.alex.mvc.render.ResultRender;

import java.io.PrintWriter;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-02 21:45
 */
public class JsonResultRender implements ResultRender {
    private Object jsonData;

    public JsonResultRender(Object jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().setContentType("application/json");
        requestProcessorChain.getResponse().setCharacterEncoding("UTF-8");

        try (PrintWriter writer = requestProcessorChain.getResponse().getWriter()) {
            Gson gson = new Gson();
            writer.write(gson.toJson(jsonData));
            writer.flush();
        }
    }
}
