package main.java.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private final String view;
    private final Map<String, Object> model;

    public ModelAndView(String view) {
        this(view, Collections.emptyMap());
    }

    public ModelAndView(String view, Map<String, Object> model) {
        this.view = view;
        this.model = new HashMap<>(model);
    }

    public String getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }
}