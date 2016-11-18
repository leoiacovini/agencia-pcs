package pcs.labsoft.agencia.aux;

import pcs.labsoft.agencia.components.interfaces.IDB;

/**
 * Created by leoiacovini on 11/18/16.
 */
public class DBHelper {

    private final IDB db;

    public DBHelper(IDB db) {
        this.db = db;
    }

    public void prepareWithSeed() {

    }

    public void cleanData() {
        db.clean();
        db.runMigrations();
    }

}
