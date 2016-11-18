package pcs.labsoft.agencia.components;

import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * Created by leoiacovini on 11/8/16.
 */
public class H2Server {

    private final Server h2Server;

    public H2Server() throws SQLException {
        this.h2Server = Server.createTcpServer();
    }

    public void startServer() throws SQLException {
        Logger.getLogger().info("Starting in memory H2 server");
        h2Server.start();
    }

    public void stopServer() {
        h2Server.stop();
        h2Server.shutdown();
    }

}
