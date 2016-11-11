package pcs.labsoft.agencia.components.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by leoiacovini on 11/4/16.
 */
public interface IDB {

    public Connection getConnection() throws SQLException;
    public void runMigrations();
    public void clean();

}
