package pcs.labsoft.agencia.components.interceptors;

import pcs.labsoft.agencia.components.interfaces.HttpInterceptor;
import pcs.labsoft.agencia.misc.HttpRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by leoiacovini on 11/7/16.
 */
public class BadRequestInterceptor implements HttpInterceptor {
    @Override
    public void intercep(HttpRequest httpRequest, HttpServletResponse httpResponse) {
        try {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
