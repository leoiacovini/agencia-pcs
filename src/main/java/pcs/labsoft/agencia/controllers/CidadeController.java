package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interceptors.BadRequestInterceptor;
import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.Cidade;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by leoiacovini on 10/7/16.
 */
public class CidadeController extends HttpController {

    static private List<Cidade> mockCidades = Cidade.mockData();

    @HttpHandler(path="/cidades/:id", method = "GET")
    public void getCidade(HttpRequest request, HttpServletResponse response) {
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

    private RequestDispatcher renderCidadesList(HttpServletRequest servletRequest) {
        servletRequest.setAttribute("cidades", mockCidades);
        return servletRequest.getRequestDispatcher(getPagePath("list.jsp"));
    }

    private RequestDispatcher renderCidadeDetails(String cidadeId, HttpServletRequest servletRequest) {
        Optional<Cidade> cidade = mockCidades.stream().filter(c -> c.getId() == Integer.parseInt(cidadeId)).findFirst();
        if (cidade.isPresent()) {
            servletRequest.setAttribute("cidade", cidade.get());
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
