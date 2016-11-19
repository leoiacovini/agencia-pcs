package pcs.labsoft.agencia.components;

import com.typesafe.config.Config;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import pcs.labsoft.agencia.components.interfaces.IWebServer;

import javax.servlet.*;
import java.io.File;

/**
 * Created by leoiacovini on 11/7/16.
 */
public class EmbeddedServer implements IWebServer {

    private final Tomcat tomcat;

    private EmbeddedServer(Config config) throws LifecycleException, ServletException {

        String webappDirLocation = "src/main/webapp";
        Logger.getLogger().info("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        this.tomcat = new Tomcat();
        tomcat.setSilent(true);

        tomcat.setPort(config.getInt("http.port"));
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

        tomcat.init();
        tomcat.start();
    }

    public void stop() throws LifecycleException {
        tomcat.stop();
        tomcat.destroy();
    }

    public static IWebServer startServer(Config config) throws ServletException, LifecycleException {
        return new EmbeddedServer(config);
    }
}
