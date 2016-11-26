package pcs.labsoft.agencia.models;

/**
 * Created by leoiacovini on 11/19/16.
 */
public class Pagamento {

    private final int id;
    private final String codigoConfirmacao;
    private final String forma;
    private final Double valor;

    public Pagamento(int id, String codigoConfirmacao, String forma, Double valor) {
        this.id = id;
        this.codigoConfirmacao = codigoConfirmacao;
        this.forma = forma;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public String getCodigoConfirmacao() {
        return codigoConfirmacao;
    }

    public String getForma() {
        return forma;
    }

    public Double getValor() {
        return valor;
    }

    public String toString() {
        return id + " - " + codigoConfirmacao + ", " + forma + ", " + valor;
    }
}
