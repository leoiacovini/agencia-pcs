package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interceptors.AgenteRequired;
import pcs.labsoft.agencia.components.interceptors.ClienteRequired;
import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.*;
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

    @HttpHandler(path = "/roteiro", method = "GET", interceptors = {AgenteRequired.class})
    public void roteiro(HttpRequest request, HttpServletResponse response) {
        try {
            List<Cidade> cidadesElegiveis = cidadeDao.loadAll().stream().filter(Cidade::temAeroporto).collect(Collectors.toList());
            request.setAttribute("cidadesElegiveis", cidadesElegiveis);
            renderRoteiro(request).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @HttpHandler(path = "/roteiro", method = "POST", interceptors = {AgenteRequired.class})
    public void startRoteiro(HttpRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Cliente cliente = new Cliente("Cliente","00312345678","1234567890","cliente@email.com","12309128901","991234567");
        int cidadeBaseId = Integer.parseInt(request.getParameter("cidadeBaseId"));
        Cidade cidadeBase = cidadeDao.findById(cidadeBaseId);
        Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
        Roteiro roteiro = new Roteiro(cliente, funcionario);
        session.setAttribute("cidadeBase", cidadeBase);
        session.setAttribute("cliente", cliente);
        session.setAttribute("roteiro", roteiro);
    }



    @HttpHandler(path = "/roteiro/add-trecho-inicial", method = "POST", interceptors = {AgenteRequired.class})
    public void addTrechoInicial(HttpRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cidade cidadeBase = (Cidade) session.getAttribute("cidadeBase");
        Roteiro roteiro = (Roteiro) session.getAttribute("roteiro");
        int transporteId = Integer.parseInt(request.getParameter("transporteId"));
        Transporte transporte = cidadeBase.getTransportesDePartida().stream().filter(t -> t.getId() == transporteId).findFirst().get();
        Trecho trechoInicial = new Trecho(cidadeBase, transporte, null, 0, true);
        roteiro.addTrecho(trechoInicial);
    }

    @HttpHandler(path = "/roteiro/add-trecho", method = "GET", interceptors = {AgenteRequired.class})
    public void renderAddTrecho(HttpRequest request, HttpServletResponse response) {

    }

    private RequestDispatcher renderRoteiro(HttpServletRequest servletRequest) {
        return servletRequest.getRequestDispatcher(getPagePath("roteiro.jsp"));
    }

    private String getPagePath(String pageName) {
        String baseDirectory = "roteiro/";
        return baseDirectory + pageName;
    }
}
