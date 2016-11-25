package pcs.labsoft.agencia.misc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;

public class HttpRequest extends HttpServletRequestWrapper {

    private HashMap<String, String> pathParams;

    public HttpRequest(HttpServletRequest request) {
        super(request);
        this.pathParams = new HashMap<>();
    }

    public String getPathParam(String key) {
        return pathParams.get(key);
    }

    public void putPathParam(String key, String val) {
        pathParams.put(key, val);
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return super.getRequestDispatcher("/resources/pages/" + path);
    }

    public RequestDispatcher getSuperRequestDispatcher(String path) {
        return super.getRequestDispatcher(path);
    }
}
