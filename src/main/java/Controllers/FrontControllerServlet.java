package main.java.Controllers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.util.ModelAndView;
import main.java.util.UrlMappingKey;

public class FrontControllerServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        Map<UrlMappingKey, Method> mappings = (Map<UrlMappingKey, Method>) getServletContext()
                .getAttribute(ContextListener.URL_MAPPINGS_ATTRIBUTE);
        String path = request.getRequestURI().substring(request.getContextPath().length());
        Method method = mappings == null ? null : mappings.get(new UrlMappingKey(path, request.getMethod()));

        if (method == null) {
            boolean urlExists = mappings != null && mappings.keySet().stream()
                    .anyMatch(key -> key.getUrl().equals(path));
            response.sendError(urlExists ? HttpServletResponse.SC_METHOD_NOT_ALLOWED : HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            Object controller = method.getDeclaringClass().getDeclaredConstructor().newInstance();
            Object result = method.invoke(controller);
            if (result instanceof ModelAndView modelAndView) {
                for (Map.Entry<String, Object> entry : modelAndView.getModel().entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(modelAndView.getView()).forward(request, response);
            } else if (result != null) {
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(result);
            }
        } catch (ReflectiveOperationException exception) {
            throw new ServletException("Impossible d'executer " + method.getName(), exception);
        }
    }
}
