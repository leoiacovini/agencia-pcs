package pcs.labsoft.agencia.aux;

import org.apache.commons.lang3.ArrayUtils;
import pcs.labsoft.agencia.components.Logger;
import pcs.labsoft.agencia.components.interfaces.IDB;

import java.sql.*;

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
             cidadeInsert + "('Minas Gerais', 'MG', 'Brasil', 3)"};
    private final String[] hoteisStubs = new String[]
            {hotelInsert + "('Ibis', 120.0, 1, 1);",
             hotelInsert + "('Hilton', 200.0, 2, 2);",
             hotelInsert + "('Holiday Inn', 100.0, 3, 3)"};
    private final String[] transportesStubs = new String[]
            {transporteInsert + "('Avião', 240.0, 2, 1, 1);",
             transporteInsert + "('Onibus', 120.0, 1, 2, 2);",
             transporteInsert + "('Onibus', 140.0, 2, 3, 3)"};

    private final String[] seedInserts = ArrayUtils.addAll(cidadesStubs, ArrayUtils.addAll(hoteisStubs, transportesStubs));

    public DBHelper(IDB db) {
        this.db = db;
    }

    public void prepareWithSeed() {
        try(Connection con = db.getConnection()) {
            Statement st = con.createStatement();
            for (String sql: seedInserts) {
                Logger.getLogger().debug("Seed DB Executing: " + sql);
                st.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
