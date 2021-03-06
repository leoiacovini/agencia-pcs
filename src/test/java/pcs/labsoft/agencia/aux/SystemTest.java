package pcs.labsoft.agencia.aux;

import org.apache.catalina.LifecycleException;
import org.junit.AfterClass;
import org.junit.Before;
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
        SystemTest.system = AppSystem.ensureSystemIsUp("test");
    }

    @Before
    public void cleanDB() {
        system.getDataBase().clean();
        system.getDataBase().runMigrations();
    }

    @AfterClass
    public static void cleanUp() throws LifecycleException {
        AppSystem.stopSystem();
    }

}
