package pcs.labsoft.agencia.misc;

import pcs.labsoft.agencia.components.interfaces.HttpInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by leoiacovini on 11/7/16.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HttpHandler {
    String path();
    String method();
    Class<? extends HttpInterceptor>[] interceptors() default {};
}
