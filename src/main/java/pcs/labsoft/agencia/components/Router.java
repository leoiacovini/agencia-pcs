package pcs.labsoft.agencia.components;

import pcs.labsoft.agencia.components.interfaces.HttpInterceptor;
import pcs.labsoft.agencia.components.interfaces.IRouter;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.misc.Route;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by leoiacovini on 11/4/16.
 */
public class Router implements IRouter {

    private final Routes routes;
    private HashMap<Class<?>, Object> controllers;
    private HashMap<Class<?>, HttpInterceptor> interceptors;

    public Router(Routes routes) {
        this.routes = routes;
        this.controllers = new HashMap<>();
        this.interceptors = new HashMap<>();
    }

    public void route(HttpRequest servletRequest, HttpServletResponse servletResponse) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        Optional<Route> matchedRoute = Arrays.stream(routes.getRoutes()).filter(route -> match(servletRequest, route)).findFirst();

        if (matchedRoute.isPresent() && matchedRoute.get().getMethod().equals(servletRequest.getMethod())) {
            Logger.getLogger().info("Matched route: " + matchedRoute.get().getPath() + matchedRoute.get().getMethod());
            Route route = matchedRoute.get();
            Method method = route.getHandler();
            Class<?> controller = route.getController();
            Logger.getLogger().info("Handler: " + method.getName());
            controllers.putIfAbsent(controller, controller.newInstance());
            runInterceptors(servletRequest, servletResponse, route);
            if (!servletResponse.isCommitted()) {
                method.invoke(controllers.get(controller), servletRequest, servletResponse);
            }
        } else {
            try {
                Logger.getLogger().info("Route not matched");
                servletRequest.getRequest().getRequestDispatcher("/resources/" + servletRequest.getPathInfo()).forward(servletRequest, servletResponse);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }

    private void runInterceptors(HttpRequest servletRequest, HttpServletResponse servletResponse, Route route) {
        Arrays.asList(route.getHandler().getAnnotation(HttpHandler.class).interceptors()).forEach(inter -> {
            try {
                interceptors.putIfAbsent(inter, inter.newInstance());
                interceptors.get(inter).intercept(servletRequest, servletResponse);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean match(HttpRequest req, Route route) {
        String path = req.getPathInfo();
        String[] splitedPath = path.split("/");
        boolean matches = true;

        String[] splitedRoutePath = route.getPath().split("/");
        if (!route.getMethod().equals(req.getMethod())) return false;
        if (splitedPath.length != splitedRoutePath.length) return false;
        for (int i = 0; i < splitedPath.length; i++) {
            String reqPart = splitedPath[i];
            String routePart = splitedRoutePath[i];
            if (routePart.startsWith(":")) {
                String pathParamName = routePart.substring(1);
                req.putPathParam(pathParamName, reqPart);
            } else if (routePart.equals("*") && (i+1) == splitedRoutePath.length && matches) {
                return true;
            } else {
                matches = matches && reqPart.matches(routePart);
                if (!matches) return false;
            }
        }
        return matches;
    }

}

