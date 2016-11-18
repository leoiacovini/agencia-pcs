package pcs.labsoft.agencia.aux;

import org.apache.catalina.LifecycleException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import pcs.labsoft.agencia.components.AppSystem;

import javax.servlet.ServletException;
import java.sql.SQLException;

/**
 * Created by leoiacovini on 11/18/16.
 */
public class SystemTest {

    @BeforeClass
    public void setUp() throws LifecycleException, ServletException, SQLException {
        AppSystem system = AppSystem.startSystem("test");
        DBHelper dbHelper = new DBHelper(system.getDataBase());
        dbHelper.cleanData();
        dbHelper.prepareWithSeed();
    }

    @AfterClass
    public void cleanUp() throws LifecycleException {
        AppSystem.stopSystem();
    }

}
