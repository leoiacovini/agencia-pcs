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
}
