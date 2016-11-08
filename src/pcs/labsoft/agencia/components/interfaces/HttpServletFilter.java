package pcs.labsoft.agencia.components.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by leoiacovini on 11/7/16.
 */
public interface HttpServletFilter {

    public void intercepRequest(HttpServletRequest httpRequest);
    public void intercepResponse(HttpServletResponse httpResponse);

}
