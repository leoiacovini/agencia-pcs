package pcs.labsoft.agencia.components.interfaces;

import pcs.labsoft.agencia.misc.HttpRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by leoiacovini on 11/7/16.
 */
public interface HttpInterceptor {

    public void intercept(HttpRequest httpRequest, HttpServletResponse httpResponse);

}
