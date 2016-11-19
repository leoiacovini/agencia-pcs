package pcs.labsoft.agencia.models;

/**
 * Created by leoiacovini on 11/19/16.
 */
public class Funcionario {

    private final int id;
    private final String username;
    private final String password;
    private final String nome;
    private final String cargo;

    public Funcionario(int id, String username, String password, String nome, String cargo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }
}
