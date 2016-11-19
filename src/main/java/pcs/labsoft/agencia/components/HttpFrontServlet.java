package pcs.labsoft.agencia.components;

import com.typesafe.config.Config;
import org.apache.catalina.LifecycleException;
import pcs.labsoft.agencia.components.interfaces.IRouter;
import pcs.labsoft.agencia.misc.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by leoiacovini on 11/7/16.
 */

public class HttpFrontServlet extends HttpServlet {

    private IRouter router;

    @Override
    public void init() throws ServletException {
        super.init();
        this.router = AppSystem.getSystem().getRouter();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpRequest newReq = new HttpRequest(req);
            router.route(newReq, resp);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
