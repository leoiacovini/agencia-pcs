package pcs.labsoft.agencia.components.interfaces;

import pcs.labsoft.agencia.components.AppSystem;

/**
 * Created by leoiacovini on 11/8/16.
 */
public class HttpController {

    private AppSystem system;
    private IDB db;

    public HttpController() {
        system = AppSystem.getSystem();
        db = system.getDataBase();
    }

}
