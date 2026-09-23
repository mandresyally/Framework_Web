package main.java.Controllers;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContext;
import main.java.annotation.Controller;
import main.java.annotation.UrlMapping;
import main.java.util.Util;
import main.java.util.UrlMappingKey;

public class ContextListener implements ServletContextListener {
    public static final String URL_MAPPINGS_ATTRIBUTE = ContextListener.class.getName() + ".urlMappings";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            init(event.getServletContext());
        } catch (Exception exception) {
            throw new IllegalStateException("Impossible de charger les controllers", exception);
        }
    }

    public void init(ServletContext context) throws Exception {
            Map<UrlMappingKey, Method> mappings = new LinkedHashMap<>();
            List<Class<?>> classes = Util.getClassesInPackage("main.java.Controllers");
            for (Class<?> controller : classes) {
                if (!controller.isAnnotationPresent(Controller.class)) {
                    continue;
                }
                System.out.println("Controller trouve: " + controller.getName());
                for (Method method : controller.getDeclaredMethods()) {
                    UrlMapping mapping = method.getAnnotation(UrlMapping.class);
                    if (mapping == null) {
                        continue;
                    }
                    String httpMethod = mapping.method().toUpperCase(Locale.ROOT);
                    UrlMappingKey key = new UrlMappingKey(mapping.value(), httpMethod);
                    if (mappings.put(key, method) != null) {
                        throw new IllegalStateException("Mapping duplique: " + httpMethod + " " + mapping.value());
                    }
                    System.out.println("Mapping trouve: " + httpMethod + " " + mapping.value()
                            + " -> " + controller.getName() + "." + method.getName());
                }
            }
            context.setAttribute(URL_MAPPINGS_ATTRIBUTE, mappings);
    }
}
