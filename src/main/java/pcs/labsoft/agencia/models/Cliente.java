package pcs.labsoft.agencia.models;

/**
 * Created by leoiacovini on 11/19/16.
 */
public class Cliente {

    private final int id;
    private final String nome;
    private final String cpf;
    private final String rg;
    private final String email;
    private final String passaporte;
    private final String telefone;

    public Cliente(int id, String nome, String cpf, String rg, String email, String passaporte, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.passaporte = passaporte;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public String getEmail() {
        return email;
    }

    public String getPassaporte() {
        return passaporte;
    }

    public String getTelefone() {
        return telefone;
    }
}
