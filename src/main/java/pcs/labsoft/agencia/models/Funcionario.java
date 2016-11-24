package pcs.labsoft.agencia.models;

import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.models.dao.FuncionarioDao;

/**
 * Created by leoiacovini on 11/19/16.
 */
public class Funcionario {

    private final int id;
    private final String username;
    private final String encryptedPassword;
    private final String nome;
    private final String cargo;

    public Funcionario(int id, String username, String encryptedPassword, String nome, String cargo) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.nome = nome;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    static public Funcionario logIn(String username, String hashedPassword, IDB db) {
        FuncionarioDao dao = new FuncionarioDao(db);
        Funcionario funcionario = dao.getByUsername(username);
        if  (funcionario != null && funcionario.encryptedPassword.equals(hashedPassword)) {
            return funcionario;
        } else {
            return null;
        }
    }

}
