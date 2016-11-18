package pcs.labsoft.agencia.components;

import com.typesafe.config.Config;
import pcs.labsoft.agencia.components.interfaces.IRouter;
import pcs.labsoft.agencia.misc.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by leoiacovini on 11/7/16.
 */

public class HttpFrontServlet extends HttpServlet {

    private final IRouter router;

    public HttpFrontServlet(IRouter router) {
        this.router = router;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Logger.getLogger().info("Front Servlet OK");
            HttpRequest newReq = new HttpRequest(req);
            router.route(newReq, resp);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
