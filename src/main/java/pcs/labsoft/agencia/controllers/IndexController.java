package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jhonata-antunes on 25/11/16.
 */
public class IndexController  extends HttpController {

    @HttpHandler(path = "/index", method = "GET")
    public void getIndex(HttpRequest request, HttpServletResponse response) {
        try {
            renderIndex(request).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private RequestDispatcher renderIndex(HttpRequest servletRequest) {
        return servletRequest.getRequestDispatcher(getPagePath("index.jsp"));
    }

    private String getPagePath(String pageName) {
        String baseDirectory = "main/";
        return baseDirectory + pageName;
    }

}