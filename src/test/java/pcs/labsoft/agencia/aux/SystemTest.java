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

    static protected AppSystem system;

    @BeforeClass
    public static void setUp() throws LifecycleException, ServletException, SQLException {
        SystemTest.system = AppSystem.startSystem("test");
        DBHelper dbHelper = new DBHelper(system.getDataBase());
        dbHelper.prepareWithSeed();
    }

    @AfterClass
    public static void cleanUp() throws LifecycleException {
        AppSystem.stopSystem();
    }

}
