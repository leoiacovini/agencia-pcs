package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interceptors.AgenteRequired;
import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.*;
import pcs.labsoft.agencia.models.dao.CidadeDao;
import pcs.labsoft.agencia.models.Cliente;
import pcs.labsoft.agencia.models.dao.ClienteDao;
import pcs.labsoft.agencia.models.dao.RoteiroDao;
import pcs.labsoft.agencia.models.graph.Graph;
import pcs.labsoft.agencia.models.graph.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by scorpion on 30/11/16.
 */
public class SugestaoController extends HttpController {

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

    @HttpHandler(path = "/sugestao/roteiro/new", method = "POST", interceptors = {AgenteRequired.class})
    public void startRoteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int cidadeBaseId = Integer.parseInt(request.getParameter("cidadeBaseId"));
        int cidadeFinalId = Integer.parseInt(request.getParameter("cidadeFinalId"));

        if (cidadeBaseId == cidadeFinalId) {
            request.setAttribute("EqualsCity","Iguais");
            newRoteiro(request,response);
        } else {
            int clienteId = Integer.parseInt(request.getParameter("clienteId"));
            Cliente cliente = new ClienteDao(db).getClienteById(clienteId);
            Cidade cidadeBase = cidadeDao.findById(cidadeBaseId);
            Cidade cidadeFinal = cidadeDao.findById(cidadeFinalId);
            Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
            int numeroPessoas = Integer.parseInt(request.getParameter("numeroPessoas"));
            Roteiro roteiro = new Roteiro(cliente, funcionario, numeroPessoas);
            session.setAttribute("cidadeBase", cidadeBase);
            session.setAttribute("cidadeFinal", cidadeFinal);
            session.setAttribute("cidadeAtual", cidadeBase);
            session.setAttribute("cliente", cliente);
            session.setAttribute("roteiro", roteiro);
            session.setAttribute("numeroPessoas", numeroPessoas);
            request.setAttribute("roteiro", roteiro);
            roteiro = Roteiro.montaRoteiro(roteiro, cidadeBase, cidadeFinal, cidadeDao);
            session.setAttribute("roteiro",roteiro);
            request.setAttribute("roteiro",roteiro);
            response.sendRedirect("/AgenciaPCS/sugestao/roteiro");
        }
    }

    @HttpHandler(path = "/sugestao/roteiro", method = "GET", interceptors = {AgenteRequired.class})
    public void roteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("roteiro", request.getSession().getAttribute("roteiro"));
        request.getRequestDispatcher("sugestao/roteiro.jsp").forward(request, response);
    }

    @HttpHandler(path = "/sugestao/updateHotel/:trechoIndex", method = "GET", interceptors = {AgenteRequired.class})
    public void getUpdateHotel(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Roteiro roteiro = (Roteiro) session.getAttribute("roteiro");
        int index = Integer.parseInt(request.getPathParam("trechoIndex"));
        Trecho trecho = roteiro.getTrecho(index);
        List<Hotel> hotelList = trecho.getCidade().getHoteis();
        request.setAttribute("hoteis", hotelList);
        request.setAttribute("trechoIndex", index);
        request.getRequestDispatcher("sugestao/updateHotel.jsp").forward(request, response);
    }

    @HttpHandler(path = "/sugestao/updateHotel/:trechoIndex", method = "POST", interceptors = {AgenteRequired.class})
    public void updateHotel(HttpRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Roteiro roteiro = (Roteiro) session.getAttribute("roteiro");
        int index = Integer.parseInt(request.getPathParam("trechoIndex"));
        int hotelId = Integer.parseInt(request.getParameter("hotelId"));
        Trecho trecho = roteiro.getTrecho(index);
        Hotel hotel = trecho.getCidade().getHotelById(hotelId);
        trecho.updateHotel(hotel);
        response.sendRedirect("/AgenciaPCS/sugestao/roteiro");
    }

    @HttpHandler(path = "/sugestao/pagamento", method = "GET", interceptors = {AgenteRequired.class})
    public void pagamento(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("valor", ((Roteiro) request.getSession().getAttribute("roteiro")).getValor());
        request.getRequestDispatcher("roteiro/pagamento.jsp").forward(request, response);
    }

    @HttpHandler(path = "/sugestao/concluir", method = "POST", interceptors = {AgenteRequired.class})
    public void concluir(HttpRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tipoPagamento = request.getParameter("tipoPagamento");
        String codigoConfirmacao = null;
        if (tipoPagamento.equals("cartao")) {
            codigoConfirmacao = request.getParameter("codigoConfirmacao");
        }
        HttpSession session = request.getSession();
        Roteiro roteiro = (Roteiro) session.getAttribute("roteiro");
        Pagamento pagamento = new Pagamento(codigoConfirmacao, tipoPagamento, roteiro.getValor());
        roteiro.setPagamento(pagamento);
        RoteiroDao roteiroDao = new RoteiroDao(db);
        Roteiro createdRoteiro = roteiroDao.create(roteiro);
        request.setAttribute("roteiro", createdRoteiro);
        clearRoteiroSession(request.getSession());
        if (createdRoteiro != null) {
            request.getRequestDispatcher("/sugestao/concluido.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void clearRoteiroSession(HttpSession session) {
        session.removeAttribute("cidadeBase");
        session.removeAttribute("cidadeAtual");
        session.removeAttribute("transporte");
        session.removeAttribute("numeroPessoas");
        session.removeAttribute("hotel");
        session.removeAttribute("roteiro");
        session.removeAttribute("duracao");
        session.removeAttribute("proximaCidade");
        session.removeAttribute("cliente");
    }
}
