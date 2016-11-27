package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jhonata-antunes on 26/11/16.
 */
public class RelatorioController {

    @HttpHandler(path = "/relatorio/vendas", method = "GET")
    public void getVendas(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("relatorio/vendas.jsp").forward(request, response);
    }

}
