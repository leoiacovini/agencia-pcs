package pcs.labsoft.agencia.components.interfaces;

import com.typesafe.config.Config;
import pcs.labsoft.agencia.components.AppSystem;

/**
 * Created by leoiacovini on 11/8/16.
 */
public class HttpController {

    protected AppSystem system;
    protected IDB db;
    protected Config config;

    public HttpController() {
        system = AppSystem.getSystem();
        db = system.getDataBase();
        config = system.getConfiguration();
    }

}
