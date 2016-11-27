package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jhonata-antunes on 26/11/16.
 */
public class RelatorioController {

    @HttpHandler(path = "/relatorio/vendas", method = "GET")
    public void getVendas(HttpRequest request, HttpServletResponse response) {
        try {
            renderVendas(request).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private RequestDispatcher renderVendas(HttpRequest servletRequest) {
        return servletRequest.getRequestDispatcher(getPagePath("vendas.jsp"));
    }

    private String getPagePath(String pageName) {
        String baseDirectory = "relatorio/";
        return baseDirectory + pageName;
    }
}
