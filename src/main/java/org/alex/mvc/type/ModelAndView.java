package org.alex.mvc.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-08-03 11:33
 */
@Getter
public class ModelAndView {

    // view path
    private String view;

    private Map<String, Object> model = new HashMap<>();

    public ModelAndView setView(String view) {
        this.view = view;
        return this;
    }

    public ModelAndView addViewData(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }
}
