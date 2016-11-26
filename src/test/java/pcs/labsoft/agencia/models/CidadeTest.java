package pcs.labsoft.agencia.models;

import org.junit.BeforeClass;
import org.junit.Test;
import pcs.labsoft.agencia.aux.SystemTest;
import pcs.labsoft.agencia.models.dao.CidadeDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by leoiacovini on 26/11/16.
 */
public class CidadeTest extends SystemTest {

    static CidadeDao cidadeDao;

    @BeforeClass
    public static void before() {
        cidadeDao = new CidadeDao(system.getDataBase());
    }

    @Test
    public void temAeroporto() {
        Cidade cidadeComAeroporto = cidadeDao.findById(2);
        Cidade cidadeSemAeroporto = cidadeDao.findById(1);
        assertTrue(cidadeComAeroporto.temAeroporto());
        assertFalse(cidadeSemAeroporto.temAeroporto());
    }

    @Test
    public void getCidadesAdjacentes() {
        Cidade cidade = cidadeDao.findById(2);
        List<Cidade> cidadesAdjacentes = cidade.getCidadesAdjacentes();
        int adjacentesCount = cidadesAdjacentes.stream().filter(c -> c.getId() == 1 || c.getId() == 3).collect(Collectors.toList()).size();
        assertEquals(2, adjacentesCount);
    }

    @Test
    public void getTransportesToCidade() {
        Cidade cidadeDeOrigem = cidadeDao.findById(2);
        Cidade cidadeDeDestino = cidadeDao.findById(3);
        List<Transporte> transportes = cidadeDeOrigem.getTransportesToCidade(cidadeDeDestino);
        assertEquals(1, transportes.size());
        assertEquals(4, transportes.get(0).getId());
    }

    @Test
    public void getTransporteDePartidaByIid() {
        Cidade cidade = cidadeDao.findById(2);
        Transporte transporte = cidade.getTransporteDePartidaById(4);
        assertNotNull(transporte);
        assertEquals(transporte.getId(), 4);
    }

    @Test
    public void getHotelById() {
        Cidade cidade = cidadeDao.findById(2);
        Hotel hotel = cidade.getHotelById(2);
        assertNotNull(hotel);
        assertEquals(2, hotel.getID());
    }

}
