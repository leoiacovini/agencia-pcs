package pcs.labsoft.agencia.models;

/**
 * Created by leoiacovini on 11/19/16.
 */
public class Trecho {

    private final Cidade cidade;
    private final Transporte transporte;
    private final Hotel hotel;
    private final boolean isTrechoInicial;
    private final int duracao;
    private final int id;

    public Trecho(Cidade cidade, Transporte transporte, Hotel hotel, int duracao, boolean isTrechoInicial, int id) {
        this.cidade = cidade;
        this.transporte = transporte;
        this.hotel = hotel;
        this.isTrechoInicial = isTrechoInicial;
        this.duracao = duracao;
        this.id = id;
    }

    public Trecho(Cidade cidade, Transporte transporte, Hotel hotel, int duracao, boolean isTrechoInicial) {
        this(cidade, transporte, hotel, duracao, isTrechoInicial, 0);
    }

    public int getId() {
        return id;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public boolean isTrechoInicial() {
        return isTrechoInicial;
    }

    public int getDuracao() {
        return duracao;
    }
}
