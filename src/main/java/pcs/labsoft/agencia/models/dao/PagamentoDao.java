package pcs.labsoft.agencia.models.dao;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.components.interfaces.ModelDao;
import pcs.labsoft.agencia.models.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhonata-antunes on 26/11/16.
 */
public class PagamentoDao extends ModelDao {

    public PagamentoDao (IDB db) {
        super(db);
    }

    public List<Pagamento> loadAll() {

        List<Pagamento> list = new ArrayList<>();

        try (Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM pagamentos");
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                int id = rs.getInt("id");
                String codigo_confirmacao = rs.getString("codigo_confirmacao");
                String forma = rs.getString("forma");
                Double valor = rs.getDouble("valor");
                Pagamento p = new Pagamento(codigo_confirmacao, forma, valor, id);
                list.add(p);
            }
        }
        catch (SQLException e) {
            if (e.getErrorCode() == 23505) {
                Logger.getLogger().warn(e.getMessage());
            } else {
                e.printStackTrace();
            }
        }

        return list;
    }

    public Pagamento create(Pagamento pagamento) {

        try (Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO pagamentos (forma, codigo_confirmacao, valor) VALUES (?, ?, ?)");
            statement.setString(1, pagamento.getForma());
            statement.setString(2, pagamento.getCodigoConfirmacao());
            statement.setString(3, pagamento.getValor().toString());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return new Pagamento(pagamento.getForma(), pagamento.getCodigoConfirmacao(), pagamento.getValor(), id);
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

    public Pagamento getById(int id) {

        try (Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM pagamentos WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                String codigo_confirmacao = rs.getString("codigo_confirmacao");
                String forma = rs.getString("forma");
                Double valor = rs.getDouble("valor");
                return new Pagamento(codigo_confirmacao, forma, valor, id);
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
