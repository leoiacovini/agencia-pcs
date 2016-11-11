package pcs.labsoft.agencia.models;

/**
 * Created by leoiacovini on 11/11/16.
 */
public class Transporte {

    private final int id;
    private final Cidade cidadeDeOrigem;
    private final Cidade cidadeDePartida;
    private final String tipo;
    private final Double preco;

    public Transporte(Cidade cidadeDeOrigem, Cidade cidadeDePartida, String tipo, Double preco, int  id) {
        this.cidadeDeOrigem = cidadeDeOrigem;
        this.cidadeDePartida = cidadeDePartida;
        this.tipo = tipo;
        this.preco = preco;
        this.id = id;
    }

}
