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

        Logger.getLogger().info("Starting up System...");

        Routes = new Routes();
        Router = new Router(Routes);
        Configuration = ConfigFactory.defaultApplication();
        ServletFilter = new ServletFilter();
        FrontServlet = new HttpFrontServlet(Configuration, Router);
        DataBase = null; //new DefaultDB(Configuration);
        Auth = new Auth(Configuration);

        Logger.getLogger().info("All components started successfully");

        switch (env) {
            case "prod": {
                startProdSystem();
                break;
            }
            case "local": {
                startLocalSystem();
                break;
            }
            case "test": {
                startTestSystem();
                break;
            }
        }
    }

    public static void startSystem(String env) throws ServletException, LifecycleException {
        AppSystem.system = new AppSystem(env);
        TomcatServer.startServer(AppSystem.system.getServlet(), AppSystem.system.getServletFilter());
    }

    public static AppSystem getSystem() {
        return AppSystem.system;
    }

    private static void startProdSystem() {

    }

    private static void startTestSystem() {

    }

    private static void startLocalSystem() {

    }

}
