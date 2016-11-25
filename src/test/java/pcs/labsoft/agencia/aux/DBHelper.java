package pcs.labsoft.agencia.aux;

import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interfaces.IDB;

import java.sql.*;
import java.util.stream.Stream;

/**
 * Created by leoiacovini on 11/18/16.
 */
public class DBHelper {

    private final IDB db;

    private final String cidadeInsert = "INSERT INTO cidades (nome, estado, pais, id) VALUES ";
    private final String hotelInsert = "INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ";
    private final String transporteInsert = "INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ";

    private final String[] cidadesStubs = new String[]
            {cidadeInsert + "('Sao Paulo', 'SP', 'Brasil', 1);",
             cidadeInsert + "('Rio de Janeiro', 'RJ', 'Brasil', 2);",
             cidadeInsert + "('Minas Gerais', 'MG', 'Brasil', 3)",
             cidadeInsert + "('Guarulhos', 'SP', 'Brasil', 4)",
             cidadeInsert + "('Paraty', 'RJ', 'Brasil', 5)",
             cidadeInsert + "('Campos do Jordão', 'SP', 'Brasil', 6)"};

    private final String[] hoteisStubs = new String[]
            {hotelInsert + "('Ibis', 120.0, 1, 1);",
             hotelInsert + "('Hilton', 200.0, 2, 2);",
             hotelInsert + "('Holiday Inn', 100.0, 3, 3)",
             hotelInsert + "('Ibis', 100.0, 4, 4)",
             hotelInsert + "('Triade', 100.0, 5, 5)",
             hotelInsert + "('Bella', 100.0, 6, 6)"};

    private final String[] transportesStubs = new String[]
            {transporteInsert + "('avião', 240.0, 2, 1, 1);",
             transporteInsert + "('onibus', 120.0, 1, 2, 2);",
             transporteInsert + "('onibus', 140.0, 2, 3, 3)"};

    private final String[] seedInserts =  Stream.of(cidadesStubs, hoteisStubs, transportesStubs).flatMap(Stream::of).toArray(String[]::new);

    public DBHelper(IDB db) {
        this.db = db;
    }

    public void prepareWithSeed() {
        try(Connection con = db.getConnection()) {
            Statement st = con.createStatement();
            for (String sql: seedInserts) {
                Logger.getLogger().info(sql);
                st.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
