package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.Cidade;
import pcs.labsoft.agencia.models.dao.CidadeDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by leoiacovini on 10/7/16.
 */
public class CidadeController extends HttpController {

    private CidadeDao CityDao = new CidadeDao(db);

    @HttpHandler(path = "/cidades/:id", method = "GET")
    public void getCidade(HttpRequest request, HttpServletResponse response) throws Exception {
        try {
            String cidadeId = request.getPathParam("id");
            RequestDispatcher requestDispatcher = renderCidadeDetails(cidadeId, request);
            requestDispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    @HttpHandler(path = "/cidades", method = "GET")
    public void listCidades(HttpRequest request, HttpServletResponse response) {
        try {
            renderCidadesList(request).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @HttpHandler(path = "/novacidade", method = "GET")
    public void newCidade(HttpRequest request, HttpServletResponse response) {
        try {
            renderNewCidade(request).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @HttpHandler(path = "/novacidade", method = "POST")
    public void addCidade(HttpRequest request, HttpServletResponse response) throws Exception {
        Cidade nova = new Cidade(request.getParameter("Nome"), request.getParameter("Estado"), request.getParameter("Pais"));
        CityDao.create(nova);
        String ok = "OK";
        request.setAttribute("Adicao", ok);
        CRUDCidade(request, response);

    }

    @HttpHandler(path = "/managercidades", method = "GET")
    public void CRUDCidade(HttpRequest request, HttpServletResponse response) {
        try {
            if (request.getAttribute("Adicao") != null) {

            } else {
                request.setAttribute("Adicao", "Null");
            }

            renderCRUDCidade(request).forward(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    private RequestDispatcher renderCidadesList(HttpServletRequest servletRequest) {
        servletRequest.setAttribute("cidades", CityDao.loadAll());
        return servletRequest.getRequestDispatcher(getPagePath("list.jsp"));
    }

    private RequestDispatcher renderCRUDCidade(HttpServletRequest servletRequest) {
        return servletRequest.getRequestDispatcher(getPagePath("options.jsp"));
    }

    private RequestDispatcher renderNewCidade(HttpServletRequest servletRequest) {
        return servletRequest.getRequestDispatcher(getPagePath("newcity.jsp"));
    }

    private RequestDispatcher renderCidadeDetails(String cidadeId, HttpServletRequest servletRequest) throws Exception {
        Cidade cidade = CityDao.findById(Integer.parseInt(cidadeId));
        if (cidade != null) {
            servletRequest.setAttribute("cidade", cidade);
            return servletRequest.getRequestDispatcher(getPagePath("details.jsp"));
        } else {
            servletRequest.setAttribute("subject", "cidade");
            return servletRequest.getRequestDispatcher("404.jsp");
        }
    }

    private String getPagePath(String pageName) {
        String baseDirectory = "cidades/";
        return baseDirectory + pageName;
    }

}
