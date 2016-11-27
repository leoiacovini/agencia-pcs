package pcs.labsoft.agencia.components.interfaces;

import org.apache.catalina.LifecycleException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

/**
 * Created by leoiacovini on 11/7/16.
 */
public interface IWebServer {

    static IWebServer startServer(Servlet servlet) throws ServletException, LifecycleException {
        return null;
    }

    void stop() throws LifecycleException;

}
