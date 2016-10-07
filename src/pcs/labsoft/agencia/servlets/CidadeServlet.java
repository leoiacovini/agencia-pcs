package pcs.labsoft.agencia.servlets;

import pcs.labsoft.agencia.models.Cidade;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

/**
 * Created by leoiacovini on 10/7/16.
 */
@WebServlet(name = "CidadeServlet", urlPatterns = "/cidades")
public class CidadeServlet extends HttpServlet {

    static private List<Cidade> mockCidades = Cidade.mockData();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidadeId = request.getParameter("id");
        RequestDispatcher requestDispatcher = (cidadeId != null) ? renderCidadeDetails(cidadeId, request) : renderCidadesList(request);
        requestDispatcher.forward(request, response);
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
            return servletRequest.getRequestDispatcher("WEB-INF/pages/404.jsp");
        }
    }

    private String getPagePath(String pageName) {
        String baseDirectory = "WEB-INF/pages/cidades/";
        return baseDirectory + pageName;
    }
}
