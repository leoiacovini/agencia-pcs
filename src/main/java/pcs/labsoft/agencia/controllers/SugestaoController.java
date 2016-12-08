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
import pcs.labsoft.agencia.models.graph.Graph;
import pcs.labsoft.agencia.models.graph.Path;

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

    @HttpHandler(path = "/sugestao/roteiro/new", method = "POST", interceptors = {AgenteRequired.class})
    public void startRoteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int cidadeBaseId = Integer.parseInt(request.getParameter("cidadeBaseId"));
        int cidadeFinalId = Integer.parseInt(request.getParameter("cidadeFinalId"));

        if (cidadeBaseId==cidadeFinalId){
            request.setAttribute("EqualsCity","Iguais");
            newRoteiro(request,response);

        } else {
            int clienteId = Integer.parseInt(request.getParameter("clienteId"));
            Cliente cliente = new ClienteDao(db).getClienteById(clienteId);
            Cidade cidadeBase = cidadeDao.findById(cidadeBaseId);
            Cidade cidadeFinal = cidadeDao.findById(cidadeFinalId);
            Funcionario funcionario = (Funcionario) session.getAttribute("funcionario");
            Roteiro roteiro = new Roteiro(cliente, funcionario);
            session.setAttribute("cidadeBase", cidadeBase);
            session.setAttribute("cidadeFinal", cidadeFinal);
            session.setAttribute("cidadeAtual", cidadeBase);
            session.setAttribute("cliente", cliente);
            session.setAttribute("roteiro", roteiro);
            request.setAttribute("roteiro", roteiro);
            montaRoteiro(request,response);
        }
    }

    private void montaRoteiro(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Graph graph;
        int  cityId;
        int transpId=0;
        Path path;
        List<Cidade> cidades = cidadeDao.loadAll();
        graph = Graph.buildFromCidades(cidades);
        int cidadeBaseId = ((Cidade) session.getAttribute("cidadeBase")).getId();
        int cidadeFinalId = ((Cidade) session.getAttribute("cidadeFinal")).getId();
        Logger.getLogger().warn("Cidade B"+cidadeBaseId);
        Logger.getLogger().warn("Cidade F"+cidadeFinalId);
        path = graph.getShortestPath(cidadeBaseId,cidadeFinalId);
        addTrecho(request,response);
        Logger.getLogger().warn("voltei");
        List<Integer> listcities = path.getNodesId();
        List<Integer> listtransp = path.getEdgesIds();
        cityId=1;

        while(listcities.get(cityId)!=listcities.size()) {
            Cidade cidadeAtual = cidadeDao.findById(listcities.get(cityId-1));
            Cidade cidadeProx = cidadeDao.findById(listcities.get(cityId));
            Transporte transporte =  cidadeAtual.getTransporteDePartidaById(listtransp.get(transpId));
            if(cityId==1){
            }

            request.getRequestDispatcher("sugestao/setHotel.jsp").forward(request, response);


        }

    }

    private void addTrecho(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        boolean inicial = isInicial(session);
        int duracao = inicial ? 0 : (int) session.getAttribute("duracao");
        Roteiro roteiro = (Roteiro) session.getAttribute("roteiro");
        Cidade cidade = inicial ? (Cidade) session.getAttribute("cidadeAtual") : (Cidade) session.getAttribute("proximaCidade");
        Transporte transporte = (Transporte) session.getAttribute("transporte");
        Hotel hotel = (Hotel) session.getAttribute("hotel");
        Trecho trecho = new Trecho(cidade, transporte, hotel, duracao, inicial);
        roteiro.addTrecho(trecho);
        if (!inicial) {
            session.setAttribute("cidadeAtual", cidade);
            session.removeAttribute("proximaCidade");
        }
    }
    private boolean isInicial(HttpSession session) {
        return session.getAttribute("proximaCidade") == null;
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
