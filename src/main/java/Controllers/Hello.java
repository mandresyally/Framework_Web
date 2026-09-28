package main.java.Controllers;

import java.util.HashMap;
import java.util.Map;
import main.java.annotation.Controller;
import main.java.annotation.UrlMapping;
import main.java.util.ModelAndView;

@Controller
public class Hello {
    @UrlMapping(value = "/api/hello", method = "GET", restAPI = true)
    public Object api() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Bonjour depuis l'API Hello");
        response.put("success", true);
        return response;
    }

    @UrlMapping(value = "/api/hello-text", method = "GET", restAPI = true)
    public Object apiText() {
        return "Bonjour depuis l'API Hello";
    }

    @UrlMapping(value = "/hello", method = "GET")
    public ModelAndView show() {
        Map<String, Object> model = new HashMap<>();
        model.put("message", "Bonjour depuis le controller Hello");
        return new ModelAndView("/WEB-INF/views/hello.jsp", model);
    }
}
