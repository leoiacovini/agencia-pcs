package pcs.labsoft.agencia.models;

import pcs.labsoft.agencia.components.interceptors.BadRequestInterceptor;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leoiacovini on 10/7/16.
 */
public class Cidade {

    private final int id;
    private final String nome;
    private final String pais;
    private final String estado;
    private List<Transporte> transportesDePartida;
    private List<Transporte> transportesDeChegada;
    private List<Hotel> hoteis;

    public Cidade(String nome, String pais, String estado, int id) {
        this.nome = nome;
        this.pais = pais;
        this.estado = estado;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }

    public String getEstado() {
        return estado;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return id + " - " + nome + ", " + estado + ", " + pais;
    }

    public static List<Cidade> mockData() {
        List<Cidade> cidades = new ArrayList<>();
        cidades.add(new Cidade("São Paulo", "Brasil", "SP", 1));
        cidades.add(new Cidade("Rio de Janeiro", "Brasil", "RJ", 2));
        cidades.add(new Cidade("Brasilia", "Brasil", "DF", 3));
        cidades.add(new Cidade("Paris", "França", "Ilha de França", 4));
        return cidades;
    }

    public List<Transporte> getTransportesDePartida() {
        return transportesDePartida;
    }

    public void addTransportesDePartida(Transporte transportesDePartida) {
        this.transportesDePartida.add(transportesDePartida);
    }

    public List<Transporte> getTransportesDeChegada() {
        return transportesDeChegada;
    }

    public void addTransportesDeChegada(Transporte transportesDeChegada) {
        this.transportesDeChegada.add(transportesDeChegada);
    }

    public List<Hotel> getHoteis() {
        return hoteis;
    }

    public void addHotel(Hotel hotel) {
        this.hoteis.add(hotel);
    }
}
