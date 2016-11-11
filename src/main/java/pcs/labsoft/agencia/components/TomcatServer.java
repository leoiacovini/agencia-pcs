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
public class TomcatServer implements IWebServer {

    private TomcatServer(Config config, Servlet servlet, Filter filter) throws ServletException, LifecycleException {

        String webappDirLocation = "src/main/webapp";

        Logger.getLogger().info("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        Tomcat tomcat = new Tomcat();
        tomcat.setSilent(true);

        tomcat.setPort(config.getInt("http.port"));
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

        tomcat.addServlet(ctx, "frontController", servlet);
        ctx.addServletMapping("/app/*", "frontController");

        FilterDef filter1definition = new FilterDef();
        filter1definition.setFilterName("servletFilter");
        filter1definition.setFilter(filter);
        ctx.addFilterDef(filter1definition);

        FilterMap filter1mapping = new FilterMap();
        filter1mapping.setFilterName("servletFilter");
        filter1mapping.addURLPattern("/*");
        ctx.addFilterMap(filter1mapping);

        tomcat.init();
        tomcat.start();
    }

    public static IWebServer startServer(Config config, Servlet servlet, Filter filter) throws ServletException, LifecycleException {
        return new TomcatServer(config, servlet, filter);
    }
}
