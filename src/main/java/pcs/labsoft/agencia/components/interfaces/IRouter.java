package pcs.labsoft.agencia.components.interfaces;

import pcs.labsoft.agencia.misc.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by leoiacovini on 11/4/16.
 */
public interface IRouter {

    void route(HttpRequest servletRequest, HttpServletResponse servletResponse) throws
            IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException;

}
