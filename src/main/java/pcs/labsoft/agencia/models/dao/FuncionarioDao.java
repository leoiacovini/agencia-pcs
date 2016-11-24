package pcs.labsoft.agencia.models.dao;

import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.models.Funcionario;

import java.sql.*;

/**
 * Created by leoiacovini on 23/11/16.
 */
public class FuncionarioDao {

    private final IDB db;

    public FuncionarioDao(IDB db) {
        this.db = db;
    }

    public Funcionario getByUsername(String username){

        try(Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM funcionarios WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Funcionario(rs.getInt("id"), username, rs.getString("encrypted_password"), rs.getString("nome"), rs.getString("cargo"));
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

}
