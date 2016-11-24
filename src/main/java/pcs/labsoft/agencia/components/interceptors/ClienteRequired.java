package pcs.labsoft.agencia.components.interceptors;

import pcs.labsoft.agencia.components.interfaces.HttpInterceptor;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.Cliente;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by leoiacovini on 24/11/16.
 */
public class ClienteRequired implements HttpInterceptor {

    @Override
    public void intercept(HttpRequest httpRequest, HttpServletResponse httpResponse) {
        HttpSession session = httpRequest.getSession();
        if (!(session.getAttribute("cliente") instanceof Cliente)) {
            try {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
