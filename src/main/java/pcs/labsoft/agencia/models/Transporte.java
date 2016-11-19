package pcs.labsoft.agencia.models;

/**
 * Created by leoiacovini on 11/11/16.
 */
public class Transporte {

    private final int id;
    private final Cidade cidadeDePartida;
    private final Cidade cidadeDeChegada;
    private final String tipo;
    private final Double preco;

    public Transporte(Cidade cidadeDePartida, Cidade cidadeDeChegada, String tipo, Double preco, int id) {
        this.cidadeDePartida = cidadeDePartida;
        this.cidadeDeChegada = cidadeDeChegada;
        this.tipo = tipo;
        this.preco = preco;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Cidade getCidadeDePartida() {
        return cidadeDePartida;
    }

    public Cidade getCidadeDeChegada() {
        return cidadeDeChegada;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getPreco() {
        return preco;
    }
}
