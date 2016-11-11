package pcs.labsoft.agencia.components.filters;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interfaces.HttpServletFilter;
import pcs.labsoft.agencia.misc.HttpFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@HttpFilter
public class LoggingFilter implements HttpServletFilter {

    @Override
    public void intercepRequest(HttpServletRequest httpRequest) {
      Logger.getLogger().info(httpRequest.getMethod() + " - " + httpRequest.getRequestURI());
    }

    @Override
    public void intercepResponse(HttpServletResponse httpResponse) {
        Logger.getLogger().info(httpResponse.getStatus() + " - " + httpResponse.getContentType());
    }

}
