package pcs.labsoft.agencia.components;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.catalina.LifecycleException;
import org.apache.tomcat.util.log.SystemLogHandler;
import pcs.labsoft.agencia.components.interfaces.*;

import javax.servlet.*;
import java.sql.SQLException;

/**
 * Created by leoiacovini on 11/4/16.
 */
public class AppSystem implements ServletContextListener {

    private IDB DataBase;
    private Config Configuration;
    private IRouter Router;
    private Routes Routes;
    private H2Server h2Server;
    private IWebServer webServer;
    private Auth Auth;

    public Config getConfiguration() { return Configuration;}
    public IDB getDataBase() { return DataBase; }
    public IRouter getRouter() { return Router; }
    public Routes getRoutes() { return Routes; }
    public static AppSystem getSystem() { return AppSystem.system; }

    static private AppSystem system;
    static private String env;

    public static void setEnv(String env) { AppSystem.env = env; }
    public static void setWebServer(IWebServer server) { AppSystem.getSystem().webServer = server; }

    public static AppSystem startSystem(String env) throws ServletException, LifecycleException, SQLException {
        Logger.getLogger().info("Starting up System...");
        AppSystem system = AppSystem.system = new AppSystem(env);
        Logger.getLogger().info("All components started successfully!");
        return system;
    }

    public static AppSystem ensureSystemIsUp(String env) throws ServletException, SQLException, LifecycleException {
        if (AppSystem.getSystem() == null) {
            AppSystem.startSystem(env);
        }
        return AppSystem.getSystem();
    }

    public static void startEmbeddedServer(String env) throws ServletException, LifecycleException {
        Config conf = ConfigFactory.defaultApplication();
        AppSystem.setEnv(env);
        IWebServer server = EmbeddedServer.startServer(conf);
        AppSystem.setWebServer(server);
    }

    public static void stopSystem() throws LifecycleException {
        if (AppSystem.getSystem().webServer != null) {
            AppSystem.system.webServer.stop();
        } else {
            AppSystem.getSystem().stop();
        }
    }

    public AppSystem() {}
    private AppSystem(String env) throws ServletException, LifecycleException, SQLException {

        AppSystem.env = env;
        String configFile;

        switch (env) {
            case "test": {
                configFile = "test.conf";
                this.h2Server = new H2Server();
                h2Server.startServer();
                break;
            }
            default: {
                configFile = "application.conf";
                break;
            }
        }
        Configuration = ConfigFactory.load(configFile);
        Routes = new Routes();
        Auth = new Auth();
        Router = new Router(Routes);
        DataBase = new DefaultDB(Configuration);
        DataBase.runMigrations();
    }

    private void stop() throws LifecycleException {
        DataBase.stop();
        if (h2Server != null) {
            h2Server.stopServer();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            AppSystem.ensureSystemIsUp(env == null ? "test" : env);
        } catch (ServletException | LifecycleException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            AppSystem.getSystem().stop();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    public Auth getAuth() {
        return Auth;
    }
}
