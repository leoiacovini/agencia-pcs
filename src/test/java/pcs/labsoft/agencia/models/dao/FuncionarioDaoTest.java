package pcs.labsoft.agencia.models.dao;

import org.junit.Test;
import pcs.labsoft.agencia.aux.SystemTest;
import pcs.labsoft.agencia.models.Funcionario;
import pcs.labsoft.agencia.models.dao.FuncionarioDao;
import static org.junit.Assert.*;

/**
 * Created by leoiacovini on 24/11/16.
 */
public class FuncionarioDaoTest extends SystemTest {

    @Test
    public void getFuncionarioByUsername() {
        FuncionarioDao dao =  new FuncionarioDao(system.getDataBase());
        Funcionario funcionario = dao.getByUsername("fulano");
        assertNotNull(funcionario);
        assertEquals("Fulano", funcionario.getNome());
        assertEquals("fulano", funcionario.getUsername());
        assertEquals("agente", funcionario.getCargo());

        Funcionario notFuncionario = dao.getByUsername("not-found");
        assertNull(notFuncionario);
    }

    @Test
    public void getFuncionarioById() {
        FuncionarioDao funcionarioDao = new FuncionarioDao(system.getDataBase());
        Funcionario funcionario = funcionarioDao.getById(1);
        assertNotNull(funcionario);
        assertEquals(1, funcionario.getId());
    }

}
