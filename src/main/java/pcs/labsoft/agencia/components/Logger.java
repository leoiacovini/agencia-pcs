package pcs.labsoft.agencia.components;

import org.slf4j.LoggerFactory;

/**
 * Created by leoiacovini on 11/7/16.
 */
public class Logger {

    private static org.slf4j.Logger logger  = LoggerFactory.getLogger("application");

    public static org.slf4j.Logger getLogger() {
        return logger;
    }

}
