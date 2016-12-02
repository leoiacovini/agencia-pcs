package pcs.labsoft.agencia.models;

import org.junit.BeforeClass;
import org.junit.Test;
import pcs.labsoft.agencia.aux.SystemTest;
import pcs.labsoft.agencia.models.dao.CidadeDao;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by leoiacovini on 26/11/16.
 */
public class CidadeTest extends SystemTest {

    private static CidadeDao cidadeDao;

    @BeforeClass
    public static void before() {
        cidadeDao = new CidadeDao(system.getDataBase());
    }

    @Test
    public void temAeroporto() {
        Cidade cidadeComAeroporto = cidadeDao.findById(1);
        Cidade cidadeSemAeroporto = cidadeDao.findById(4);
        assertTrue(cidadeComAeroporto.temAeroporto());
        assertFalse(cidadeSemAeroporto.temAeroporto());
    }

    @Test
    public void getCidadesAdjacentes() {
        Cidade cidade = cidadeDao.findById(1);
        List<Cidade> cidadesAdjacentes = cidade.getCidadesAdjacentes();

        int adjacentesCount = cidadesAdjacentes.stream().filter(c -> c.getId() == 2 || c.getId() == 3 || c.getId() == 4).collect(Collectors.toList()).size();
        assertEquals(3, adjacentesCount);
    }

    @Test
    public void getTransportesToCidade() {
        Cidade cidadeDeOrigem = cidadeDao.findById(1);
        Cidade cidadeDeDestino = cidadeDao.findById(2);
        List<Transporte> transportes = cidadeDeOrigem.getTransportesToCidade(cidadeDeDestino);
        assertEquals(3, transportes.size());
    }

    @Test
    public void getTransporteDePartidaByIid() {
        Cidade cidade = cidadeDao.findById(1);
        Transporte transporte = cidade.getTransporteDePartidaById(1);
        assertNotNull(transporte);
        assertEquals(transporte.getId(), 1);
    }

    @Test
    public void getHotelById() {
        Cidade cidade = cidadeDao.findById(1);
        Hotel hotel = cidade.getHotelById(1);
        assertNotNull(hotel);
        assertEquals(1, hotel.getID());
    }

}
