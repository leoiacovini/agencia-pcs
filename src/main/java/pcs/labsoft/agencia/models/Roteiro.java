package pcs.labsoft.agencia.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leoiacovini on 11/19/16.
 */
public class Roteiro {

    private final List<Trecho> trechos;
    private final Cliente cliente;
    private final Funcionario funcionario;
    private Pagamento pagamento;
    private int duracao;
    private final int id;

    public Roteiro(Cliente cliente, Funcionario funcionario, int id) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.trechos = new ArrayList<>();
        this.id = id;
    }

    public Roteiro(Cliente cliente, Funcionario funcionario) {
        this(cliente, funcionario, 0);
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
        return duracao;
    }

    public int getId() {
        return id;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void addTrecho(Trecho trecho) {
        this.trechos.add(trecho);
        this.duracao += trecho.getDuracao();
    }

    public void removeLastTrecho() {
        int index = this.trechos.size() - 1;
        Trecho lastTrecho = trechos.get(index);
        this.trechos.remove(index);
        this.duracao -= lastTrecho.getDuracao();
    }

    public Double getValor() {
        return trechos.stream().map(Trecho::getValor).reduce(0.0, (acc, v) -> acc + v);
    }
}
