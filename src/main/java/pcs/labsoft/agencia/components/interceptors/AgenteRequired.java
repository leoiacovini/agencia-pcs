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
        Object user = httpRequest.getSession().getAttribute("funcionario");
        if (!(user instanceof Funcionario) || !((Funcionario) user).getCargo().equals("agente")) {
            try {
                httpResponse.sendRedirect("/AgenciaPCS/login");
                //httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
