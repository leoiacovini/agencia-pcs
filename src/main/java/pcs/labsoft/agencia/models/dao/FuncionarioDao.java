package pcs.labsoft.agencia.models.dao;

import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.components.interfaces.ModelDao;
import pcs.labsoft.agencia.models.Funcionario;

import java.sql.*;

/**
 * Created by leoiacovini on 23/11/16.
 */
public class FuncionarioDao extends ModelDao {

    public FuncionarioDao(IDB db) {
        super(db);
    }

    public Funcionario getByUsername(String username){

        try(Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM funcionarios WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Funcionario(username, rs.getString("encrypted_password"), rs.getString("nome"), rs.getString("cargo"), rs.getInt("id"));
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

}
