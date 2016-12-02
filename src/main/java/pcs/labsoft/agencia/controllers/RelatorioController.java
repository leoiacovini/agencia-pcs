package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.Roteiro;
import pcs.labsoft.agencia.models.dao.RoteiroDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by jhonata-antunes on 26/11/16.
 */
public class RelatorioController extends HttpController {

    @HttpHandler(path = "/relatorio/vendas", method = "GET")
    public void getVendas(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        RoteiroDao roteiroDao = new RoteiroDao(db);
        List<Roteiro> roteiros = roteiroDao.loadAll();
        request.setAttribute("roteiros", roteiros);
        request.getRequestDispatcher("relatorio/vendas.jsp").forward(request, response);
    }

}
