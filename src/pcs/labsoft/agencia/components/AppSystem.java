package pcs.labsoft.agencia.components;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.catalina.LifecycleException;
import pcs.labsoft.agencia.components.interfaces.*;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

/**
 * Created by leoiacovini on 11/4/16.
 */
public class AppSystem {

    private final IDB DataBase;
    private final Config Configuration;
    private final IRouter Router;
    private final IAuth Auth;
    private final Routes Routes;
    private final Servlet FrontServlet;

    public Filter getServletFilter() {
        return ServletFilter;
    }

    private final Filter ServletFilter;

    static private AppSystem system;

    public Config getConfiguration() {
        return Configuration;
    }

    public IDB getDataBase() { return DataBase; }

    public IRouter getRouter() {
        return Router;
    }

    public IAuth getAuth() {
        return Auth;
    }

    public Routes getRoutes() { return Routes; }

    public Servlet getServlet() { return FrontServlet; }


    private AppSystem(String env) throws ServletException, LifecycleException {

        String configFile = "application.conf";

        switch (env) {
            case "test": {
                H2Server.startServer();
                configFile = "test.conf";
                break;
            }
        }

        Routes = new Routes();
        Router = new Router(Routes);
        Configuration = ConfigFactory.load(configFile);
        DataBase = new DefaultDB(Configuration);
        ServletFilter = new ServletFilter();
        FrontServlet = new HttpFrontServlet(Router);
        Auth = new Auth(Configuration);

        TomcatServer.startServer(FrontServlet, ServletFilter);

    }

    public static void startSystem(String env) throws ServletException, LifecycleException {
        Logger.getLogger().info("Starting up System...");
        AppSystem.system = new AppSystem(env);
        Logger.getLogger().info("All components started successfully");
    }

    public static AppSystem getSystem() {
        return AppSystem.system;
    }


}
