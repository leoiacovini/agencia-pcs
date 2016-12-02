package pcs.labsoft.agencia.models.dao;

import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.components.interfaces.ModelDao;
import pcs.labsoft.agencia.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leoiacovini on 26/11/16.
 */
public class RoteiroDao extends ModelDao {

    public RoteiroDao(IDB db) {
        super(db);
    }

    public List<Roteiro> loadAll() {

        try (Connection connection = db.getConnection()) {

            List<Roteiro> roteiros = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM roteiros");
            ResultSet roteirosRs = statement.executeQuery();

            while (roteirosRs.next()) {
                int clienteId = roteirosRs.getInt("cliente_id");
                int pagamentoid = roteirosRs.getInt("pagamento_id");
                int funcionarioId = roteirosRs.getInt("funcionario_id");
                int roteiroId = roteirosRs.getInt("id");

                ClienteDao clienteDao = new ClienteDao(db);
                Cliente cliente = clienteDao.getClienteById(clienteId);

                FuncionarioDao funcionarioDao = new FuncionarioDao(db);
                Funcionario funcionario = funcionarioDao.getById(funcionarioId);

                PagamentoDao pagamentoDao = new PagamentoDao(db);
                Pagamento pagamento = pagamentoDao.getById(pagamentoid);

                Roteiro roteiro = new Roteiro(cliente, funcionario, roteiroId);
                roteiro.setPagamento(pagamento);

                PreparedStatement trechoStatement = connection.prepareStatement("SELECT * FROM trechos WHERE roteiro_id = ?");
                trechoStatement.setInt(1, roteiroId);
                ResultSet trechoRs = trechoStatement.executeQuery();

                while (trechoRs.next()) {
                    int trechoId = trechoRs.getInt("id");
                    boolean isTrechoInicial = trechoRs.getBoolean("is_trecho_inicial");
                    int duracao = trechoRs.getInt("duracao");
                    int cidadeId = trechoRs.getInt("cidade_id");
                    int transporteId = trechoRs.getInt("transporte_id");
                    int hotelId = trechoRs.getInt("hotel_id");

                    CidadeDao cidadeDao = new CidadeDao(db);
                    Cidade cidade = cidadeDao.findById(cidadeId);
                    Hotel hotel;
                    Transporte transporte;

                    if (hotelId == 0) {
                        hotel = null;
                    } else {
                        hotel = cidade.getHotelById(hotelId);
                    }
                    if (transporteId == 0) {
                        transporte = null;
                    } else {
                        transporte = cidade.getTransporteDeChegadaById(transporteId);
                    }
                    Trecho trecho = new Trecho(cidade, transporte, hotel, duracao, isTrechoInicial, trechoId);
                    roteiro.addTrecho(trecho);
                }
                roteiros.add(roteiro);
            }
            return roteiros;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    public Roteiro create(Roteiro roteiro) {

        try (Connection connection = db.getConnection()) {

            int pagamentoId;
            int roteiroId;

            Pagamento storedPagamento;
            Roteiro storedRoteiro;

            // 1 - Create Pagamento
            Pagamento pagamento = roteiro.getPagamento();
            PagamentoDao pagamentoDao = new PagamentoDao(db);
            storedPagamento = pagamentoDao.create(pagamento);
            if (storedPagamento == null) throw new SQLException("Pagamento não pode ser criado");

            // 2 - Create Roteiro
            PreparedStatement roteiroStatement = connection.prepareStatement("INSERT INTO roteiros (duracao, funcionario_id, cliente_id, pagamento_id) VALUES (? ,? ,?, ?)");
            roteiroStatement.setInt(1, roteiro.getDuracao());
            roteiroStatement.setInt(2, roteiro.getFuncionario().getId());
            roteiroStatement.setInt(3, roteiro.getCliente().getId());
            roteiroStatement.setInt(4, storedPagamento.getId());
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
                if (trecho.getHotel() == null) {
                    trechoStatement.setNull(4, Types.INTEGER);
                } else {
                    trechoStatement.setInt(4, trecho.getTransporte().getId());
                }
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
