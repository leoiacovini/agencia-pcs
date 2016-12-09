package pcs.labsoft.agencia.models;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import pcs.labsoft.agencia.components.AppSystem;
import pcs.labsoft.agencia.models.dao.FuncionarioDao;
import pcs.labsoft.agencia.aux.SystemTest;
import pcs.labsoft.agencia.models.*;
import pcs.labsoft.agencia.models.dao.CidadeDao;
import pcs.labsoft.agencia.models.dao.ClienteDao;
import pcs.labsoft.agencia.models.dao.RoteiroDao;
/**
 * Created by scorpion on 08/12/16.
 */
public class SugestaoTest extends SystemTest {
    private static Cidade CidadeOrigem;
    private static Cidade CidadeDestino;
    private static Cliente cliente;
    private static Funcionario funcionario;

    @Test
    public void create() {
        CidadeDao cidadeDao = new CidadeDao(system.getDataBase());
        ClienteDao clienteDao = new ClienteDao(system.getDataBase());
        cliente = clienteDao.getClienteById(1);
        Cidade cidadeOrigem = cidadeDao.getCidadesComAeroporto().get(0);
        Cidade cidadeDestino = cidadeDao.getCidadesComAeroporto().get(2);
        FuncionarioDao funcionarioDao = new FuncionarioDao(system.getDataBase());
        funcionario = funcionarioDao.getByUsername("fulano");

        Roteiro roteiro = new Roteiro(cliente, funcionario, 1);
        roteiro = Roteiro.montaRoteiro(roteiro, cidadeOrigem,cidadeDestino,cidadeDao);
        assertNotNull(roteiro);
        assertEquals(cliente,roteiro.getCliente());
        assertEquals(3,roteiro.getTrechos().size());
        assertEquals(funcionario,roteiro.getFuncionario());
    }

}