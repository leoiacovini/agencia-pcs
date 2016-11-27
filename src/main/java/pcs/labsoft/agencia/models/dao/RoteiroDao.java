package pcs.labsoft.agencia.models.dao;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.components.interfaces.ModelDao;
import pcs.labsoft.agencia.models.Cliente;
import pcs.labsoft.agencia.models.Funcionario;
import pcs.labsoft.agencia.models.Pagamento;
import pcs.labsoft.agencia.models.Roteiro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhonata-antunes on 26/11/16.
 */
public class RoteiroDao extends ModelDao {

    public RoteiroDao (IDB db) {
        super(db);
    }

    public List<Roteiro> loadAll() {

        List<Roteiro> list = new ArrayList<>();
        FuncionarioDao funcionarioDao = new FuncionarioDao(db);
        ClienteDao clienteDao = new ClienteDao(db);
        PagamentoDao pagamentoDao = new PagamentoDao(db);

        try (Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM roteiros");
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                int id = rs.getInt("id");
                int duracao = rs.getInt("duracao");
                int funcionarioId = rs.getInt("funcionario_id");
                int pagamentoId = rs.getInt("pagamento_id");
                int clienteId = rs.getInt("cliente_id");
                Funcionario funcionario = funcionarioDao.getById(funcionarioId);
                Cliente cliente = clienteDao.getClienteById(clienteId);
                Pagamento pagamento = pagamentoDao.getById(pagamentoId);
                Roteiro roteiro = new Roteiro(cliente, funcionario, id);
                roteiro.setDuracao(duracao);
                roteiro.setPagamento(pagamento);
                list.add(roteiro);
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

    public Roteiro create(Roteiro roteiro) {

        try (Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO roteiros (duracao, funcionario_id, cliente_id, pagamento_id) VALUES (?, ?, ?, ?)");
            statement.setInt(1, roteiro.getDuracao());
            statement.setInt(2, roteiro.getFuncionario().getId());
            statement.setInt(3, roteiro.getCliente().getId());
            statement.setInt(4, roteiro.getPagamento().getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return new Roteiro(roteiro.getCliente(), roteiro.getFuncionario(), roteiro.getId());
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
}
