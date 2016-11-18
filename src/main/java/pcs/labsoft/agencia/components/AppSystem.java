package pcs.labsoft.agencia.components;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.catalina.LifecycleException;
import org.h2.tools.Server;
import pcs.labsoft.agencia.components.interfaces.*;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.sql.SQLException;

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
    private final String env;
    private final H2Server h2Server;
    private final IWebServer webServer;

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


    private AppSystem(String env) throws ServletException, LifecycleException, SQLException {

        String configFile = "application.conf";
        this.env = env;
        switch (env) {
            case "test": {
                this.h2Server = new H2Server();
                h2Server.startServer();
                configFile = "test.conf";
                break;
            }
            default: {
                h2Server = null;
            }
        }

        Routes = new Routes();
        Router = new Router(Routes);
        Configuration = ConfigFactory.load(configFile);
        DataBase = new DefaultDB(Configuration);
        DataBase.runMigrations();
        ServletFilter = new ServletFilter();
        FrontServlet = new HttpFrontServlet(Router);
        Auth = new Auth(Configuration);

        this.webServer = TomcatServer.startServer(Configuration, FrontServlet, ServletFilter);

    }

    public static AppSystem startSystem(String env) throws ServletException, LifecycleException, SQLException {
        Logger.getLogger().info("Starting up System...");
        AppSystem.system = new AppSystem(env);
        Logger.getLogger().info("All components started successfully");
        return AppSystem.system;
    }

    private void stop() throws LifecycleException {
        switch (this.env) {
            case "test": {
                h2Server.stopServer();
            }
        }
        this.webServer.stop();
    }

    public static void stopSystem() throws LifecycleException {
        AppSystem.system.stop();
    }

    public static AppSystem getSystem() {
        return AppSystem.system;
    }

}
