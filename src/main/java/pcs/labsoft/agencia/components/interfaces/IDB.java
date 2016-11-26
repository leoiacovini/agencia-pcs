package pcs.labsoft.agencia.components.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by leoiacovini on 11/4/16.
 */
public interface IDB {

    Connection getConnection() throws SQLException;
    void runMigrations();
    void clean();
    void stop();

}
