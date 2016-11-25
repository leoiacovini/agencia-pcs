package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interceptors.AgenteRequired;
import pcs.labsoft.agencia.components.interceptors.ClienteRequired;
import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.Cidade;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by adilsontorres on 18/11/16.
 */
public class RoteiroController extends HttpController {

    private final CidadeDao cidadeDao;

    public RoteiroController() {
        cidadeDao = new CidadeDao(db);
    }

    @HttpHandler(path = "/roteiro", method = "GET", interceptors = AgenteRequired.class)
    public void roteiro(HttpRequest request, HttpServletResponse response) {
        try {
            List<Cidade> cidadesElegiveis = cidadeDao.loadAll().stream().filter(Cidade::temAeroporto).collect(Collectors.toList());
            request.setAttribute("cidadesElegiveis", cidadesElegiveis);
            renderRoteiro(request).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @HttpHandler(path = "/start-roteiro", method = "POST", interceptors = {AgenteRequired.class, ClienteRequired.class})
    public void startRoteiro(HttpRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
        Roteiro roteiro = new Roteiro(cliente, funcionario);
        session.setAttribute("roteiro", roteiro);
    }

    private RequestDispatcher renderRoteiro(HttpServletRequest servletRequest) {
        return servletRequest.getRequestDispatcher(getPagePath("roteiro.jsp"));
    }

    private String getPagePath(String pageName) {
        String baseDirectory = "roteiro/";
        return baseDirectory + pageName;
    }
}
