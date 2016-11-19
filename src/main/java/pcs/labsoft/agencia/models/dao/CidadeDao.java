package pcs.labsoft.agencia.models.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.models.Cidade;
import pcs.labsoft.agencia.models.Hotel;
import pcs.labsoft.agencia.models.Transporte;


/**
 * Created by jhonata-antunes on 11/11/16.
 */
public class CidadeDao {

	private final IDB idb;
	
	public CidadeDao (IDB idb) {
		this.idb = idb;
	}
	
	/**
	Load all objects of type Cidade from DB.
	@return List containing all objects of type Cidade.
	*/
	public List<Cidade> loadAll() {
		HashMap<Integer, Cidade> map = new HashMap<>();

        try (Connection connection = idb.getConnection()) {

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
                Transporte transoprte = new Transporte(cidadePartida, cidadeChegada, tipo, preco, id);
                cidadePartida.addTransportesDePartida(transoprte);
                cidadeChegada.addTransportesDeChegada(transoprte);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger().info(ex.getMessage());
            return null;
        }
		
		return new ArrayList<>(map.values());
	}

	/**
	Load one object by id.
	@param  Objects id in DB.
	@return Object of type Cidade, or null.
	@throws thrown when two objects are loaded by the same id.
	*/
	public Cidade findById(int id) throws Exception {

        try(Connection connection = idb.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM cidades WHERE id=" + id);
            if (rs.next()) {
                return new Cidade(rs.getString("nome"), rs.getString("pais"), rs.getString("estado"), id);
            } else {
                return null;
            }
		}
		catch (SQLException ex) {
            ex.printStackTrace();
			Logger.getLogger().error(ex.getMessage());
			return null;
		}

	}
	
	/**
	Create/Insert new object.
	@param  New object to be saved.
	@throws thrown when Create/Insert fails.
	*/
	public int create(Cidade cidade) throws Exception {
		
		try(Connection connection = idb.getConnection()) {
			Statement statement = connection.createStatement();
			String SQLInsert = "INSERT INTO cidades ";
			String SQLColumns = "(nome, estado, pais) ";
			String SQLValues = "VALUES ( '" + cidade.getNome() + "' , '" + cidade.getEstado() + "' , '" + cidade.getPais() + "')";
			statement.executeUpdate(SQLInsert + SQLColumns + SQLValues);
            ResultSet genKeys = statement.getGeneratedKeys();
            if (genKeys.next()) {
                return genKeys.getInt(1);
            } else {
                throw new SQLException("No rows have been created");
            }
		}
		catch (SQLException ex) {
            ex.printStackTrace();
			Logger.getLogger().error(ex.getMessage());
            return -1;
		}
	}

	/**
	Update an object.
	@param  Object to be updated.
	@throws thrown when update fails.
	*/
	public void update(Cidade cidade) throws Exception {
		
		try (Connection connection = idb.getConnection()) {
			Statement statement = connection.createStatement();
			String SQLUpdate = "UPDATE cidades ";
			String SQLSet = "SET nome = '" + cidade.getNome() + "' , estado = '" + cidade.getEstado() + "' , pais = '" + cidade.getPais() + "' ";
			String SQLWhere = "WHERE id = " + cidade.getId();
			statement.executeUpdate(SQLUpdate + SQLSet + SQLWhere);
		} catch (SQLException ex) {
			ex.printStackTrace();
			Logger.getLogger().error("Erro ao atualizar registro (id = "+ cidade.getId() +").");
		}
	}

	/**
	Delete row, by id, from the table.
	@param  Id from object to be deleted.
	@throws thrown when delete fails.
	*/
	public void deleteById(int id) throws Exception {
		try(Connection connection = idb.getConnection()) {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM cidades WHERE id = " + id);
		} catch (SQLException ex) {
            ex.printStackTrace();
			Logger.getLogger().info(ex.getMessage());
		}
	}
}
