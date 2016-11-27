package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interceptors.AgenteRequired;
import pcs.labsoft.agencia.components.interceptors.ClienteRequired;
import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.*;
import pcs.labsoft.agencia.models.dao.CidadeDao;
import pcs.labsoft.agencia.models.dao.ClienteDao;
import pcs.labsoft.agencia.models.dao.RoteiroDao;

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

    @HttpHandler(path = "/roteiro/new", method = "GET", interceptors = {AgenteRequired.class})
    public void newRoteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        clearRoteiroSession(request);
        ClienteDao clienteDao = new ClienteDao(db);
        List<Cidade> cidadesElegiveis = cidadeDao.getCidadesComAeroporto();
        List<Cliente> clientes = clienteDao.getAllClientes();
        request.setAttribute("clientes", clientes);
        request.setAttribute("cidadesElegiveis", cidadesElegiveis);
        request.getRequestDispatcher("roteiro/newRoteiro.jsp").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/new", method = "POST", interceptors = {AgenteRequired.class})
    public void startRoteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int cidadeBaseId = Integer.parseInt(request.getParameter("cidadeBaseId"));
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
        response.sendRedirect("/AgenciaPCS/roteiro/get-proxima-cidade");
    }

    @HttpHandler(path = "/roteiro", method = "GET", interceptors = {AgenteRequired.class})
    public void roteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("roteiro", request.getSession().getAttribute("roteiro"));
        request.getRequestDispatcher("roteiro/roteiro.jsp").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/get-proxima-cidade", method = "GET", interceptors = {AgenteRequired.class})
    public void getProximasCidades(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        List<Cidade> proximasCidades = cidadeAtual.getCidadesAdjacentes();
        request.setAttribute("proximasCidades", proximasCidades);
        request.getRequestDispatcher("roteiro/proximaCidade.jsp").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/set-proxima-cidade", method = "POST", interceptors = {AgenteRequired.class})
    public void setProximaCidade(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int proximaCidadeId = Integer.parseInt(request.getParameter("proximaCidadeId"));
        Cidade proximaCidade = cidadeDao.findById(proximaCidadeId);
        session.setAttribute("proximaCidade", proximaCidade);
        response.sendRedirect("/AgenciaPCS/roteiro/get-transportes");
    }

    @HttpHandler(path = "/roteiro/get-transportes", method = "GET", interceptors = {AgenteRequired.class})
    public void getTransportesToCidade(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        Cidade proximaCidade = (Cidade) session.getAttribute("proximaCidade");
        List<Transporte> transportes = cidadeAtual.getTransportesToCidade(proximaCidade);
        request.setAttribute("transportes", transportes);
        request.getRequestDispatcher("roteiro/setTransporte.jsp").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/set-transporte", method = "POST", interceptors = {AgenteRequired.class})
    public void setTransporte(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        int transporteId = Integer.parseInt(request.getParameter("transporteId"));
        Transporte transporte = cidadeAtual.getTransporteDePartidaById(transporteId);
        session.setAttribute("transporte", transporte);
        if (isInicial(session)) {
            addTrecho(request, response);
        } else {
            response.sendRedirect("/AgenciaPCS/roteiro/get-hoteis");
        }
    }

    @HttpHandler(path = "/roteiro/get-hoteis", method = "GET", interceptors = {AgenteRequired.class})
    public void getHoteis(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        List<Hotel> hotels = cidadeAtual.getHoteis();
        request.setAttribute("hoteis", hotels);
        request.getRequestDispatcher("roteiro/setHotel.jsp").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/set-hotel", method = "POST", interceptors = {AgenteRequired.class})
    public void setHotel(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int hotelId = Integer.parseInt(request.getParameter("hotelId"));
        int duracao = Integer.parseInt(request.getParameter("duracao"));
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        Hotel hotel = cidadeAtual.getHotelById(hotelId);
        session.setAttribute("duracao", duracao);
        session.setAttribute("hotel", hotel);
        addTrecho(request, response);
    }

    @HttpHandler(path = "/roteiro/pagamento", method = "GET", interceptors = {AgenteRequired.class})
    public void pagamento(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("valor", ((Roteiro) request.getSession().getAttribute("roteiro")).getValor());
        request.getRequestDispatcher("roteiro/pagamento.jsp").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/concluir", method = "POST", interceptors = {AgenteRequired.class})
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
        if (createdRoteiro != null) {
            request.getRequestDispatcher("/roteiro/concluido.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void addTrecho(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Roteiro roteiro = (Roteiro) session.getAttribute("roteiro");
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        Transporte transporte = (Transporte) session.getAttribute("transporte");
        Hotel hotel = (Hotel) session.getAttribute("hotel");
        boolean inicial = isInicial(session);
        int duracao = inicial ? 0 : (int) session.getAttribute("duracao");
        Trecho trecho = new Trecho(cidadeAtual, transporte, hotel, duracao, inicial);
        roteiro.addTrecho(trecho);
        session.setAttribute("cidadeAtual", session.getAttribute("proximaCidade"));
        session.removeAttribute("proximaCidade");
        response.sendRedirect("/AgenciaPCS/roteiro");
    }

    private boolean isInicial(HttpSession session) {
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        Cidade cidadeBase = (Cidade) session.getAttribute("cidadeBase");
        return cidadeBase.getId() == cidadeAtual.getId();
    }

    private void clearRoteiroSession(HttpRequest request) {
        request.getSession().removeAttribute("cidadeBase");
        request.getSession().removeAttribute("cidadeAtual");
        request.getSession().removeAttribute("transporte");
        request.getSession().removeAttribute("hotel");
        request.getSession().removeAttribute("roteiro");
        request.getSession().removeAttribute("proximaCidade");
        request.getSession().removeAttribute("cliente");
    }
}
