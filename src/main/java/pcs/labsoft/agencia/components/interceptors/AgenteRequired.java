package pcs.labsoft.agencia.components.interceptors;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interfaces.HttpInterceptor;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.Funcionario;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by leoiacovini on 23/11/16.
 */
public class AgenteRequired implements HttpInterceptor {

    @Override
    public void intercept(HttpRequest httpRequest, HttpServletResponse httpResponse) {
        Logger.getLogger().info(httpRequest.getSession().getAttributeNames().nextElement());
        Object user = httpRequest.getSession().getAttribute("user");
        if (!(user instanceof Funcionario)) {
            try {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
