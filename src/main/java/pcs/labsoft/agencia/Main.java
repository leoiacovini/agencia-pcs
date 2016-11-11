package pcs.labsoft.agencia;


import org.apache.catalina.LifecycleException;
import pcs.labsoft.agencia.components.AppSystem;

import javax.servlet.ServletException;

/**
 * Created by leoiacovini on 11/4/16.
 */
public class Main {

    static public void main(String[] args) throws LifecycleException, ServletException {
        AppSystem.startSystem("test");
    }

}
