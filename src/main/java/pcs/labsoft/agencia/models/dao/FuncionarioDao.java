package pcs.labsoft.agencia.models.dao;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.components.interfaces.ModelDao;
import pcs.labsoft.agencia.models.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public Funcionario getById(int id) {

        try (Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM funcionarios WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String cargo = rs.getString("cargo");
                String username = rs.getString("username");
                String password = rs.getString("encrypted_password");
                return new Funcionario(username, password, nome, cargo, id);
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            if (e.getErrorCode() == 23505) {
                Logger.getLogger().warn(e.getMessage());
            } else {
                e.printStackTrace();
            }
            return null;
        }
    }

}
