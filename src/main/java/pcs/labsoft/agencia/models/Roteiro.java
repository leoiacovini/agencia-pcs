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

}
