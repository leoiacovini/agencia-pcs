package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.dao.CidadeDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by adilsontorres on 18/11/16.
 */
public class RoteiroController extends HttpController{
    private CidadeDao CityDao = new CidadeDao(db);

    @HttpHandler(path = "/roteiro", method = "GET")
    public void Roteiro(HttpRequest request, HttpServletResponse response) {
        try {
            renderRoteiro(request).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private RequestDispatcher renderRoteiro(HttpServletRequest servletRequest) {
        return servletRequest.getRequestDispatcher(getPagePath("roteiro.jsp"));
    }

    private String getPagePath(String pageName) {
        String baseDirectory = "roteiro/";
        return baseDirectory + pageName;
    }
}