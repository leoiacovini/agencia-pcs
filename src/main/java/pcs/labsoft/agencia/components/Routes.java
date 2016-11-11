package pcs.labsoft.agencia.components;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.Route;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by leoiacovini on 11/7/16.
 */
public class Routes {

    private Route[] routes;

    public Route[] getRoutes() {
        return routes;
    }

    public Routes() {
        MethodAnnotationsScanner anSc = new MethodAnnotationsScanner();
        Reflections reflections = new Reflections("pcs.labsoft.agencia", anSc);
        Set<Method> handlers = reflections.getMethodsAnnotatedWith(HttpHandler.class);
        this.routes = handlers.stream().map( m -> {
            HttpHandler an = m.getAnnotation(HttpHandler.class);
            return new Route(an.method(), an.path(), m.getDeclaringClass(), m);
        }).toArray(Route[]::new);
    }

}
