package pcs.labsoft.agencia.models.dao;

import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.components.interfaces.ModelDao;
import pcs.labsoft.agencia.models.Pagamento;
import pcs.labsoft.agencia.models.Roteiro;
import pcs.labsoft.agencia.models.Trecho;

import java.sql.*;

/**
 * Created by leoiacovini on 26/11/16.
 */
public class RoteiroDao extends ModelDao {

    public RoteiroDao(IDB db) {
        super(db);
    }

    public Roteiro create(Roteiro roteiro) {

        try (Connection connection = db.getConnection()) {

            int pagamentoId;
            int roteiroId;

            Pagamento storedPagamento;
            Roteiro storedRoteiro;

            // 1 - Create Pagamento
            PreparedStatement pagamentoStatement = connection.prepareStatement("INSERT INTO pagamentos (forma, codigo_confirmacao, valor) VALUES (?, ?, ?)");
            Pagamento pagamento = roteiro.getPagamento();
            pagamentoStatement.setString(1, pagamento.getForma());
            pagamentoStatement.setString(2, pagamento.getCodigoConfirmacao());
            pagamentoStatement.setDouble(3, pagamento.getValor());
            pagamentoStatement.executeUpdate();
            ResultSet pagamentoRs = pagamentoStatement.getGeneratedKeys();
            if (pagamentoRs.next()) {
                pagamentoId = pagamentoRs.getInt(1);
                storedPagamento = new Pagamento(pagamento.getCodigoConfirmacao(), pagamento.getForma(), pagamento.getValor(), pagamentoId);
            } else {
                throw new SQLException("Pagamento não pode ser criado");
            }

            // 2 - Create Roteiro
            PreparedStatement roteiroStatement = connection.prepareStatement("INSERT INTO roteiros (duracao, funcionario_id, cliente_id, pagamento_id) VALUES (? ,? ,?, ?)");
            roteiroStatement.setInt(1, roteiro.getDuracao());
            roteiroStatement.setInt(2, roteiro.getFuncionario().getId());
            roteiroStatement.setInt(3, roteiro.getCliente().getId());
            roteiroStatement.setInt(4, pagamentoId);
            roteiroStatement.executeUpdate();
            ResultSet roteiroRs = roteiroStatement.getGeneratedKeys();
            if (roteiroRs.next()) {
                roteiroId = roteiroRs.getInt(1);
                storedRoteiro = new Roteiro(roteiro.getCliente(), roteiro.getFuncionario(), roteiroId);
                storedRoteiro.setPagamento(storedPagamento);
            } else {
                throw new SQLException("Roteiro não pode ser criado");
            }

            // 3 - Create Trechos
            for (Trecho trecho:roteiro.getTrechos()) {
                PreparedStatement trechoStatement = connection.prepareStatement("INSERT INTO trechos (duracao, cidade_id, hotel_id, transporte_id, roteiro_id, is_trecho_inicial) VALUES (? ,?, ?, ?, ?, ?)");
                trechoStatement.setInt(1, trecho.getDuracao());
                trechoStatement.setInt(2, trecho.getCidade().getId());
                if (trecho.getHotel() == null) {
                    trechoStatement.setNull(3, Types.INTEGER);
                } else {
                    trechoStatement.setInt(3, trecho.getHotel().getID());
                }
                trechoStatement.setInt(4, trecho.getTransporte().getId());
                trechoStatement.setInt(5, roteiroId);
                trechoStatement.setBoolean(6, trecho.isTrechoInicial());
                trechoStatement.executeUpdate();
                ResultSet trechoRs = trechoStatement.getGeneratedKeys();
                if (trechoRs.next()) {
                    storedRoteiro.addTrecho(new Trecho(trecho.getCidade(), trecho.getTransporte(), trecho.getHotel(), trecho.getDuracao(), trecho.isTrechoInicial(), trechoRs.getInt(1)));
                } else {
                    throw new SQLException("Trecho não pode ser criado");
                }
            }

            return storedRoteiro;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
