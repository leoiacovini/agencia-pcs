package pcs.labsoft.agencia.models;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.dao.CidadeDao;
import pcs.labsoft.agencia.models.graph.Graph;
import pcs.labsoft.agencia.models.graph.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by leoiacovini on 11/19/16.
 */
public class Roteiro {

    private final List<Trecho> trechos;
    private final Cliente cliente;
    private final Funcionario funcionario;
    private Pagamento pagamento;
    private final int id;
    private int numeroPessoas;

    public Roteiro(Cliente cliente, Funcionario funcionario, int numeroPessoas, int id) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.trechos = new ArrayList<>();
        this.numeroPessoas = numeroPessoas;
        this.id = id;
    }

    public Roteiro(Cliente cliente, Funcionario funcionario, int numeroPessoas) {
        this(cliente, funcionario, numeroPessoas, 0);
    }

    public List<Trecho> getTrechos() {
        return trechos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public int getDuracao() {
        return trechos.stream().mapToInt(Trecho::getDuracao).reduce(0, (acc, d) -> acc + d);
    }

    public int getId() {
        return id;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public void addTrecho(Trecho trecho) {
        this.trechos.add(trecho);
    }

    public void removeLastTrecho() {
        this.trechos.remove(this.trechos.size() - 1);
    }

    public Double getValor() {
        return trechos.stream().map(t -> t.getValor(this.numeroPessoas)).reduce(0.0, (acc, v) -> acc + v);
    }

    public static Roteiro montaRoteiro(Roteiro roteiro, Cidade cidadeOrigem, Cidade cidadeFinal, CidadeDao cidadeDao)  {
        int cityId;
        int transpId = 0;
        Path path;
        Graph graph = Graph.buildFromCidades(cidadeDao.loadAll());
        int cidadeBaseId = cidadeOrigem.getId();
        int cidadeFinalId = cidadeFinal.getId();
        path = graph.getShortestPath(cidadeBaseId,cidadeFinalId);
        List<Integer> listcities = path.getNodesId();
        List<Integer> listtransp = path.getEdgesIds();
        for (int i:listcities) {
            Logger.getLogger().warn("Id Cidade: " + i);
        }
        for (int i:listtransp) {
            Logger.getLogger().warn("Id Transp: " + i);
        }
        Trecho trecho = new Trecho(cidadeOrigem, null, null, 0, true);
        roteiro.addTrecho(trecho);
        cityId = 1;
        while (cityId!=listcities.size()) {
            Cidade cidadeAtual = cidadeDao.findById(listcities.get(cityId - 1));
            Cidade cidadeProx = cidadeDao.findById(listcities.get(cityId));
            Transporte transporte =  cidadeAtual.getTransporteDePartidaById(listtransp.get(transpId));
            Logger.getLogger().warn("Transp escolhido: " + transporte.getId());
            List<Hotel> hoteis = cidadeProx.getHoteis();
            Collections.sort(hoteis); // Ordena os hoteis por preço
            Hotel hotel =cidadeProx.getHotelById(hoteis.get(hoteis.size()/2).getID());
            Logger.getLogger().warn("Hotel escolhido: "+hotel.getID());
            trecho = new Trecho(cidadeProx, transporte, hotel,5,false);
            roteiro.addTrecho(trecho);
            cityId++;
            transpId++;
        }
        return roteiro;
    }

    public int getNumeroPessoas() {
        return this.numeroPessoas;
    }

    public Trecho getTrecho(int index) {
        return this.trechos.get(index);
    }
}
