package pcs.labsoft.agencia.misc;

import java.lang.reflect.Method;

public class Route {

    private final String method;
    private final String path;

    public Class<?> getController() {
        return controller;
    }

    private final Class<?> controller;
    private final Method handler;

    public Route(String method, String path, Class<?> controller, Method handler) {
        this.method = method;
        this.path = path;
        this.controller = controller;
        this.handler = handler;
    }

    public Method getHandler() {
        return handler;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

}
