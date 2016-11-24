package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interceptors.AgenteRequired;
import pcs.labsoft.agencia.components.interceptors.ClienteRequired;
import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.Cliente;
import pcs.labsoft.agencia.models.Funcionario;
import pcs.labsoft.agencia.models.Roteiro;
import pcs.labsoft.agencia.models.dao.CidadeDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by adilsontorres on 18/11/16.
 */
public class RoteiroController extends HttpController {

    private final CidadeDao CityDao;

    public RoteiroController() {
        CityDao = new CidadeDao(db);
    }

    @HttpHandler(path = "/roteiro", method = "GET", interceptors = AgenteRequired.class)
    public void roteiro(HttpRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Funcionario funcionario = (Funcionario) session.getAttribute("user");

            renderRoteiro(request).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @HttpHandler(path = "/start-roteiro", method = "POST", interceptors = {AgenteRequired.class, ClienteRequired.class})
    public void startRoteiro(HttpRequest request, HttpServletResponse response) {

    }

    private RequestDispatcher renderRoteiro(HttpServletRequest servletRequest) {
        return servletRequest.getRequestDispatcher(getPagePath("roteiro.jsp"));
    }

    private String getPagePath(String pageName) {
        String baseDirectory = "roteiro/";
        return baseDirectory + pageName;
    }
}
