package pcs.labsoft.agencia.models.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.h2.command.Prepared;
import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.components.interfaces.ModelDao;
import pcs.labsoft.agencia.models.Cidade;
import pcs.labsoft.agencia.models.Hotel;
import pcs.labsoft.agencia.models.Transporte;


/**
 * Created by jhonata-antunes on 11/11/16.
 */
public class CidadeDao extends ModelDao {
	
	public CidadeDao (IDB db) {
		super(db);
	}

	public List<Cidade> loadAll() {
		HashMap<Integer, Cidade> map = new HashMap<>();

        try (Connection connection = db.getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet rsCidades = statement.executeQuery("SELECT * FROM cidades");
            while (rsCidades.next()) {
                int id = rsCidades.getInt("id");
                String nome = rsCidades.getString("nome");
                String pais = rsCidades.getString("pais");
                String estado = rsCidades.getString("estado");
                map.put(id, new Cidade(nome, pais, estado, id));
            }

            ResultSet rsHoteis = statement.executeQuery("SELECT * FROM hoteis");
            while (rsHoteis.next()) {
                int id = rsHoteis.getInt("id");
                int cidadeId = rsHoteis.getInt("cidade_id");
                String nome = rsHoteis.getString("nome");
                Double preco = rsHoteis.getDouble("preco");
                Cidade cidade = map.get(cidadeId);
                cidade.addHotel(new Hotel(nome, preco, id, cidade));
            }

            ResultSet rsTransportes = statement.executeQuery("SELECT * FROM transportes");
            while (rsTransportes.next()) {
                int id = rsTransportes.getInt("id");
                int cidadePartidaId = rsTransportes.getInt("cidade_partida_id");
                int cidadeChegadaId = rsTransportes.getInt("cidade_chegada_id");
                String tipo = rsTransportes.getString("tipo");
                Double preco = rsTransportes.getDouble("preco");
                Cidade cidadePartida = map.get(cidadePartidaId);
                Cidade cidadeChegada = map.get(cidadeChegadaId);
                Transporte transporte = new Transporte(cidadePartida, cidadeChegada, tipo, preco, id);
                cidadePartida.addTransportesDePartida(transporte);
                cidadeChegada.addTransportesDeChegada(transporte);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger().info(ex.getMessage());
            return null;
        }
		
		return new ArrayList<>(map.values());
	}


	public Cidade findById(int id) {
	    List<Cidade> cidades = loadAll();
        Optional<Cidade> cidade = cidades.stream().filter(c -> c.getId() == id).findFirst();
        return cidade.orElse(null);
	}

	public Cidade create(Cidade cidade) throws Exception {
		try (Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO cidades (nome, estado, pais) VALUES (?, ?, ?)");
			statement.setString(1, cidade.getNome());
            statement.setString(2, cidade.getEstado());
            statement.setString(3, cidade.getPais());
			statement.executeUpdate();
            ResultSet genKeys = statement.getGeneratedKeys();
            if (genKeys.next()) {
                int cidadeId =  genKeys.getInt(1);
                return new Cidade(cidade.getNome(), cidade.getPais(), cidade.getEstado(), cidadeId);
            } else {
                throw new SQLException("No rows have been created");
            }
		}
		catch (SQLException ex) {
            ex.printStackTrace();
			Logger.getLogger().error(ex.getMessage());
            return null;
		}
	}

	public void update(Cidade cidade) throws Exception {
		try (Connection connection = db.getConnection()) {
			PreparedStatement statement = connection.prepareStatement("UPDATE cidades SET nome = ?, estado = ?, pais = ? WHERE id = ?");
			statement.setString(1, cidade.getNome());
            statement.setString(2, cidade.getEstado());
            statement.setString(3, cidade.getPais());
            statement.setInt(4, cidade.getId());
			statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			Logger.getLogger().error("Erro ao atualizar registro (id = "+ cidade.getId() +").");
		}
	}

	public void deleteById(int id) throws Exception {
		try (Connection connection = db.getConnection()) {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM cidades WHERE id = ?");
            statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException ex) {
            ex.printStackTrace();
			Logger.getLogger().info(ex.getMessage());
		}
	}
}
