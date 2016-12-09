package pcs.labsoft.agencia.models.dao;

import org.junit.Test;
import pcs.labsoft.agencia.aux.SystemTest;
import pcs.labsoft.agencia.models.*;
import pcs.labsoft.agencia.models.dao.CidadeDao;
import pcs.labsoft.agencia.models.dao.ClienteDao;
import pcs.labsoft.agencia.models.dao.FuncionarioDao;
import pcs.labsoft.agencia.models.dao.RoteiroDao;

import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by leoiacovini on 26/11/16.
 */
public class RoteiroDaoTest extends SystemTest {

    @Test
    public void create() {
        CidadeDao cidadeDao = new CidadeDao(system.getDataBase());
        ClienteDao clienteDao = new ClienteDao(system.getDataBase());
        Cliente cliente = clienteDao.getClienteById(1);
        FuncionarioDao funcionarioDao = new FuncionarioDao(system.getDataBase());
        Funcionario funcionario = funcionarioDao.getByUsername("fulano");

        Roteiro roteiro = new Roteiro(cliente, funcionario, 1);

        Cidade cidadeBase = cidadeDao.getCidadesComAeroporto().get(0);
        List<Cidade> cidadesAdjacentes = cidadeBase.getCidadesAdjacentes();
        Cidade proximaCidade = cidadesAdjacentes.get(0);
        List<Transporte> transportes = cidadeBase.getTransportesToCidade(proximaCidade);
        Transporte transporte1 = transportes.get(0);

        Cidade ultimaCidade = proximaCidade.getCidadesAdjacentes().get(0);
        Transporte ultimoTransporte = proximaCidade.getTransportesToCidade(ultimaCidade).get(0);
        Hotel hotel = ultimaCidade.getHoteis().get(0);

        Trecho trecho1 = new Trecho(cidadeBase, transporte1, null, 0, true);
        Trecho trecho2 = new Trecho(proximaCidade, ultimoTransporte, hotel, 5, false);

        roteiro.addTrecho(trecho1);
        roteiro.addTrecho(trecho2);
        Pagamento pagamento = new Pagamento("123456", "cartao", roteiro.getValor());
        roteiro.setPagamento(pagamento);

        RoteiroDao roteiroDao = new RoteiroDao(system.getDataBase());
        Roteiro createdRoteiro = roteiroDao.create(roteiro);

        assertNotNull(createdRoteiro);
        assertEquals(roteiro.getDuracao(), createdRoteiro.getDuracao());
        assertEquals(roteiro.getValor(), createdRoteiro.getValor());
        assertEquals(cliente.getId(), createdRoteiro.getCliente().getId());
        assertEquals(funcionario.getId(), createdRoteiro.getFuncionario().getId());
        assertEquals(pagamento.getCodigoConfirmacao(), createdRoteiro.getPagamento().getCodigoConfirmacao());
        assertEquals(2, createdRoteiro.getTrechos().size());
    }

    @Test
    public void loadAll() {
        RoteiroDao roteiroDao = new RoteiroDao(system.getDataBase());
        List<Roteiro> roteiros = roteiroDao.loadAll();

        assertEquals(1, roteiros.size());

        Roteiro roteiro = roteiros.get(0);
        assertNotNull(roteiro);
        assertEquals(1, roteiro.getId());
        assertEquals(1, roteiro.getCliente().getId());
        assertEquals(1740.0, roteiro.getValor(), 0.001);
        assertEquals(2, roteiro.getTrechos().size());
        assertEquals(1, roteiro.getPagamento().getId());
        assertEquals(5, roteiro.getDuracao());
    }

}
