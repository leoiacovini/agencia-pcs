package pcs.labsoft.agencia.models.dao;

import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.components.interfaces.ModelDao;
import pcs.labsoft.agencia.models.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by leoiacovini on 24/11/16.
 */
public class ClienteDao extends ModelDao {

    public ClienteDao(IDB db) {
        super(db);
    }

    public Cliente createCliente(Cliente cliente) {

        try (Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO clientes (nome, rg, cpf, passaporte, email, telefone) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getRg());
            statement.setString(3, cliente.getCpf());
            statement.setString(4, cliente.getPassaporte());
            statement.setString(5, cliente.getEmail());
            statement.setString(6, cliente.getTelefone());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return new Cliente(id, cliente.getNome(), cliente.getCpf(), cliente.getRg(), cliente.getEmail(), cliente.getPassaporte(), cliente.getTelefone());
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cliente getClienteById(int id) {

        try (Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clientes WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Cliente(id, rs.getString("nome"), rs.getString("cpf"), rs.getString("rg"), rs.getString("email"), rs.getString("passaporte"), rs.getString("telefone"));
            } else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
