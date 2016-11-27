package pcs.labsoft.agencia.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by leoiacovini on 10/7/16.
 */
public class Cidade {

    private int id;
    private final String nome;
    private final String pais;
    private final String estado;
    private List<Transporte> transportesDePartida;
    private List<Transporte> transportesDeChegada;
    private List<Hotel> hoteis;

    public Cidade(String nome, String pais, String estado, int id) {
        this(nome, pais, estado);
        this.id = id;
    }
    
    public Cidade(String nome, String pais, String estado) {
        this.transportesDeChegada = new ArrayList<>();
        this.transportesDePartida = new ArrayList<>();
        this.hoteis = new ArrayList<>();
        this.nome = nome;
        this.pais = pais;
        this.estado = estado;
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

    public boolean temAeroporto() {
        return transportesDePartida.stream().filter(t -> t.getTipo().equals("avião")).count() > 0;
    }

    public List<Cidade> getCidadesAdjacentes() {
        return getTransportesDePartida().stream().map(Transporte::getCidadeDeChegada).collect(Collectors.toList());
    }

    public List<Transporte> getTransportesToCidade(Cidade proximaCidade) {
        return getTransportesDePartida().stream().filter(t -> t.getCidadeDeChegada().getId() == proximaCidade.getId()).collect(Collectors.toList());
    }

    public Transporte getTransporteDePartidaById(int transporteId) {
        return getTransportesDePartida().stream().filter(t -> t.getId() == transporteId).findFirst().get();
    }

    public Hotel getHotelById(int hotelId) {
        return getHoteis().stream().filter(h -> h.getID() == hotelId).findFirst().get();
    }

}
