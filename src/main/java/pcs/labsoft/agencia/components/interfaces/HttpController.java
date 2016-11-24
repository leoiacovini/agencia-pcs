package pcs.labsoft.agencia.components.interfaces;

import com.typesafe.config.Config;
import pcs.labsoft.agencia.components.AppSystem;
import pcs.labsoft.agencia.components.Auth;

/**
 * Created by leoiacovini on 11/8/16.
 */
public class HttpController {

    protected AppSystem system;
    protected IDB db;
    protected Config config;
    protected Auth auth;

    public HttpController() {
        system = AppSystem.getSystem();
        db = system.getDataBase();
        config = system.getConfiguration();
        auth = system.getAuth();
    }

}
