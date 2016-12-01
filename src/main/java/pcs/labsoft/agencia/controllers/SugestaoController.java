package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interceptors.AgenteRequired;
import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.*;
import pcs.labsoft.agencia.models.dao.CidadeDao;
import pcs.labsoft.agencia.models.Cliente;
import pcs.labsoft.agencia.models.dao.ClienteDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by scorpion on 30/11/16.
 */
public class SugestaoController extends HttpController{

    private final CidadeDao cidadeDao;

    public SugestaoController(){
        cidadeDao = new CidadeDao(db);
    }

    @HttpHandler(path = "/sugestao/roteiro/new", method = "GET", interceptors = {AgenteRequired.class})
    public void newRoteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearRoteiroSession(request.getSession());
        ClienteDao clienteDao = new ClienteDao(db);
        List<Cidade> cidadesElegiveis = cidadeDao.getCidadesComAeroporto();
        List<Cliente> clientes = clienteDao.getAllClientes();
        request.setAttribute("clientes", clientes);
        request.setAttribute("cidadesElegiveis", cidadesElegiveis);
        request.getRequestDispatcher("sugestao/newRoteiro.jsp").forward(request, response);
    }

    private void clearRoteiroSession(HttpSession session) {
        session.removeAttribute("cidadeBase");
        session.removeAttribute("cidadeAtual");
        session.removeAttribute("transporte");
        session.removeAttribute("hotel");
        session.removeAttribute("roteiro");
        session.removeAttribute("duracao");
        session.removeAttribute("proximaCidade");
        session.removeAttribute("cliente");
    }
}
