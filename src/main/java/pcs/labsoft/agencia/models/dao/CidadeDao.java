package pcs.labsoft.agencia.models.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import pcs.labsoft.agencia.components.interfaces.IDB;
import pcs.labsoft.agencia.models.Cidade;
import pcs.labsoft.agencia.models.Hotel;
import pcs.labsoft.agencia.models.Transporte;

public class CidadeDao {
	
	private Connection connection;  
	private Statement statement;
	private IDB idb;
	
	public CidadeDao (IDB idb) {
		this.idb = idb;
	}
	
	public void LoadAll() {
		HashMap<Integer, Cidade> map = new HashMap<Integer, Cidade>(); 
		
		try {
			connection = idb.getConnection();
			statement = connection.createStatement();

			ResultSet rsCidades = statement.executeQuery("SELECT * from cidades;");
			ResultSet rsHoteis = statement.executeQuery("SELECT * from hoteis;");
			ResultSet rsTransportes = statement.executeQuery("SELECT * from transportes;");
			
			while (rsCidades.next()) {
				int id = rsCidades.getInt("id");
				String nome = rsCidades.getString("nome");
				String pais = rsCidades.getString("pais");
				String estado = rsCidades.getString("estado");
				map.put(id, new Cidade(nome, pais, estado, id));
			}
			
			while (rsHoteis.next()) {
				int id = rsHoteis.getInt("id");
				int cidadeId = rsHoteis.getInt("cidadeId");
				String nome = rsHoteis.getString("nome");
				Double preco = rsHoteis.getDouble("preco");
				Cidade cidade = map.get(cidadeId);
				cidade.addHotel(new Hotel(nome, preco, id, cidade));
			}
			
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
			
			connection.close();
		}
		catch (SQLException ex) {
			
		}
		finally {
			
		}
	}
}
