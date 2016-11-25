package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.Logger;
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

    @HttpHandler(path = "/roteiro/new", method = "GET", interceptors = {AgenteRequired.class})
    public void newRotero(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        List<Cidade> cidadesElegiveis = cidadeDao.loadAll().stream().filter(Cidade::temAeroporto).collect(Collectors.toList());
        request.setAttribute("cidadesElegiveis", cidadesElegiveis);
        request.getRequestDispatcher("roteiro/newRoteiro.jsp").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/new", method = "POST", interceptors = {AgenteRequired.class})
    public void startRoteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cliente cliente = new Cliente("Cliente", "00312345678", "1234567890", "cliente@email.com", "12309128901", "991234567");
        int cidadeBaseId = Integer.parseInt(request.getParameter("cidadeBaseId"));
        Cidade cidadeBase = cidadeDao.findById(cidadeBaseId);
        Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
        Roteiro roteiro = new Roteiro(cliente, funcionario);
        session.setAttribute("cidadeBase", cidadeBase);
        session.setAttribute("cidadeAtual", cidadeBase);
        session.setAttribute("cliente", cliente);
        session.setAttribute("roteiro", roteiro);
        request.setAttribute("roteiro", roteiro);
        request.getSuperRequestDispatcher("/roteiro/get-proxima-cidade").forward(request, response);
    }

    @HttpHandler(path = "/roteiro", method = "GET", interceptors = {AgenteRequired.class})
    public void roteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("roteiro/roteiro.jsp").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/get-proxima-cidade", method = "GET", interceptors = {AgenteRequired.class})
    public void getProximasCidades(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        List<Cidade> proximasCidades = cidadeAtual.getTransportesDePartida().stream().map(t -> t.getCidadeDeChegada()).collect(Collectors.toList());
        request.setAttribute("proximasCidades", proximasCidades);
        request.getRequestDispatcher("roteiro/proximaCidade.jsp").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/set-proxima-cidade", method = "POST", interceptors = {AgenteRequired.class})
    public void setProximaCidade(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int proximaCidadeId = Integer.parseInt(request.getParameter("proximaCidadeId"));
        Cidade proximaCidade = cidadeDao.findById(proximaCidadeId);
        session.setAttribute("proximaCidade", proximaCidade);
        request.getSuperRequestDispatcher("/roteiro/get-transportes").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/get-transportes", method = "GET", interceptors = {AgenteRequired.class})
    public void getTransportesToCidade(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        Cidade proximaCidade = (Cidade) session.getAttribute("proximaCidade");
        List<Transporte> transportes = cidadeAtual.getTransportesDePartida().stream().filter(t -> t.getCidadeDeChegada().getId() == proximaCidade.getId()).collect(Collectors.toList());
        request.setAttribute("transportes", transportes);
        request.getRequestDispatcher("roteiro/setTransporte.jsp").forward(request, response);
    }

    @HttpHandler(path = "/roteiro/set-transporte", method = "POST", interceptors = {AgenteRequired.class})
    public void setTransporte(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        int transporteId = Integer.parseInt(request.getParameter("transporteId"));
        Transporte transporte = cidadeAtual.getTransportesDePartida().stream().filter(t -> t.getId() == transporteId).findFirst().get();
        session.setAttribute("transporte", transporte);
        if (isInicial(session)) {
            addTrecho(request, response);
        } else {
            request.getSuperRequestDispatcher("/roteiro/get-hoteis").forward(request, response);
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
        Hotel hotel = cidadeAtual.getHoteis().stream().filter(h -> h.getID() == hotelId).findFirst().get();
        session.setAttribute("duracao", duracao);
        session.setAttribute("hotel", hotel);
        addTrecho(request, response);
    }

    //@HttpHandler(path = "/roteiro/add-trecho", method = "POST", interceptors = {AgenteRequired.class})
    private void addTrecho(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Roteiro roteiro = (Roteiro) session.getAttribute("roteiro");
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        Transporte transporte = (Transporte) session.getAttribute("transporte");
        Hotel hotel = null;
        boolean inicial = isInicial(session);
        if (!inicial) {
            hotel = (Hotel) session.getAttribute("hotel");
        }
        int duracao = (int) session.getAttribute("duracao");
        Trecho trecho = new Trecho(cidadeAtual, transporte, hotel, duracao, inicial);
        roteiro.addTrecho(trecho);
        session.setAttribute("cidadeAtual", session.getAttribute("proximaCidade"));
        request.getSuperRequestDispatcher("/roteiro").forward(request, response);
    }

    private boolean isInicial(HttpSession session) {
        Cidade cidadeAtual = (Cidade) session.getAttribute("cidadeAtual");
        Cidade cidadeBase = (Cidade) session.getAttribute("cidadeBase");
        return cidadeBase.getId() == cidadeAtual.getId();
    }

}
