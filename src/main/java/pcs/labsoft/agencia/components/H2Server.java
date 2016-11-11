package pcs.labsoft.agencia.components;

import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * Created by leoiacovini on 11/8/16.
 */
public class H2Server {

    public static void startServer() {
        Logger.getLogger().info("Starting in memory H2 server");
        try {
            Server h2Server = Server.createTcpServer();
            h2Server.start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
