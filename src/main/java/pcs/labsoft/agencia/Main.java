package pcs.labsoft.agencia;


import org.apache.catalina.LifecycleException;
import pcs.labsoft.agencia.components.AppSystem;

import javax.servlet.ServletException;
import java.sql.SQLException;

/**
 * Created by leoiacovini on 11/4/16.
 */
public class Main {

    static public void main(String[] args) throws LifecycleException, ServletException, SQLException {
        AppSystem.startSystem("test");
    }

}
