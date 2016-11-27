package pcs.labsoft.agencia.models.dao;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.components.interfaces.ModelDao;
import pcs.labsoft.agencia.models.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                return new Cliente(cliente.getNome(), cliente.getCpf(), cliente.getRg(), cliente.getEmail(), cliente.getPassaporte(), cliente.getTelefone(), id);
            } else {
                return null;
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 23505) {
                Logger.getLogger().warn(e.getMessage());
            } else {
                e.printStackTrace();
            }
            return null;
        }
    }

    public List<Cliente> getAllClientes() {

        try (Connection connection = db.getConnection()) {
            List<Cliente> clientes = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clientes");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getString("nome"), rs.getString("cpf"), rs.getString("rg"), rs.getString("email"), rs.getString("passaporte"), rs.getString("telefone"), rs.getInt("id"));
                clientes.add(cliente);
            }
            return clientes;
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
                return new Cliente(rs.getString("nome"), rs.getString("cpf"), rs.getString("rg"), rs.getString("email"), rs.getString("passaporte"), rs.getString("telefone"), id);
            } else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
