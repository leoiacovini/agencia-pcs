package pcs.labsoft.agencia.components;

import org.reflections.Reflections;
import pcs.labsoft.agencia.components.interfaces.HttpServletFilter;
import pcs.labsoft.agencia.misc.HttpFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by leoiacovini on 11/7/16.
 */
public class ServletFilter implements Filter {

    private final HttpServletFilter[] filters;

    public ServletFilter() {
        Reflections reflections = new Reflections("pcs.labsoft.agencia");
        Set<Class<?>> filtersClasses = reflections.getTypesAnnotatedWith(HttpFilter.class);
        this.filters = filtersClasses.stream().map( fc -> {
            try {
                return fc.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).toArray(HttpServletFilter[]::new);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rep = (HttpServletResponse) servletResponse;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        for (HttpServletFilter filter : filters) { filter.intercepRequest(req); }

        if (path.startsWith("/resources/")) {
            filterChain.doFilter(req, rep); // Goes to default servlet.
        } else {
            req.getRequestDispatcher("/app" + path).forward(req, rep); // Goes to front controller.
        }

        for (HttpServletFilter filter : filters) { filter.intercepResponse(rep); }

    }

    @Override
    public void destroy() {

    }

}

