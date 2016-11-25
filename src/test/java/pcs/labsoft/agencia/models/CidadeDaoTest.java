package pcs.labsoft.agencia.models;

import org.junit.BeforeClass;
import org.junit.Test;
import pcs.labsoft.agencia.aux.SystemTest;
import pcs.labsoft.agencia.models.dao.CidadeDao;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by leoiacovini on 11/18/16.
 */
public class CidadeDaoTest extends SystemTest {

    private static CidadeDao cidadeDao;

    @BeforeClass
    public static void before() {
        cidadeDao = new CidadeDao(system.getDataBase());
    }

    @Test
    public void loadAll() {
        List<Cidade> cidades = cidadeDao.loadAll();
        assertEquals(3, cidades.size());
    }

    @Test
    public void createCidade() throws Exception {
        Cidade cidade = new Cidade("Paris", "França", "Ilha de França", 4);
        Cidade newCidade = cidadeDao.create(cidade);
        assertNotNull(newCidade);
        assertEquals(newCidade.getNome(), cidade.getNome());
    }

    @Test
    public void findCidade() throws Exception {
        Cidade cidade = cidadeDao.findById(1);
        assertEquals(cidade.getNome(), "Sao Paulo");
        assertEquals(cidade.getEstado(), "SP");
        assertEquals(cidade.getPais(), "Brasil");
    }

    @Test
    public void updateCidade() throws Exception {
        Cidade updatedCidade = new Cidade("Updated", "Pais", "PCS", 1);
        cidadeDao.update(updatedCidade);
        updatedCidade = cidadeDao.findById(1);
        assertEquals("Updated", updatedCidade.getNome());
        assertEquals("Pais", updatedCidade.getPais());
        assertEquals("PCS", updatedCidade.getEstado());
    }

    @Test
    public void deleteById() throws Exception {
        cidadeDao.deleteById(1);
        Cidade cidade = cidadeDao.findById(1);
        assertNull(cidade);
    }

}
