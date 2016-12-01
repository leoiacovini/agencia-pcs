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
        if (request.getAttribute("EqualsCity") == null){
            request.setAttribute("EqualsCity","null");
        }
        request.getRequestDispatcher("sugestao/newRoteiro.jsp").forward(request, response);
    }

    @HttpHandler(path = "/sugestao/roteiro/new", method = "POST", interceptors = {AgenteRequired.class})
    public void startRoteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int cidadeBaseId = Integer.parseInt(request.getParameter("cidadeBaseId"));
        int cidadeFinalId = Integer.parseInt(request.getParameter("cidadeFinalId"));

        if (cidadeBaseId==cidadeFinalId){
            request.setAttribute("EqualsCity","Iguais");
            newRoteiro(request,response);

        }else {
            int clienteId = Integer.parseInt(request.getParameter("clienteId"));
            Cliente cliente = new ClienteDao(db).getClienteById(clienteId);
            Cidade cidadeBase = cidadeDao.findById(cidadeBaseId);
            Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
            Roteiro roteiro = new Roteiro(cliente, funcionario);
            session.setAttribute("cidadeBase", cidadeBase);
            session.setAttribute("cidadeAtual", cidadeBase);
            session.setAttribute("cliente", cliente);
            session.setAttribute("roteiro", roteiro);
            request.setAttribute("roteiro", roteiro);
        }
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
