package pcs.labsoft.agencia.models;

import org.junit.Test;
import pcs.labsoft.agencia.aux.SystemTest;
import static org.junit.Assert.*;

/**
 * Created by leoiacovini on 24/11/16.
 */
public class FuncionarioTest extends SystemTest {

    @Test
    public void loginFuncionario() {
        Funcionario funcionario = Funcionario.logIn("fulano", "teste", system.getDataBase(), system.getAuth());
        assertNotNull(funcionario);
        assertEquals("Fulano", funcionario.getNome());
        assertEquals(1, funcionario.getId());
        assertEquals("agente", funcionario.getCargo());

        Funcionario wrongPassFuncionario = Funcionario.logIn("fulano", "wrong", system.getDataBase(), system.getAuth());
        assertNull(wrongPassFuncionario);

        Funcionario wrongUsernameFuncionario = Funcionario.logIn("wrong", "teste", system.getDataBase(), system.getAuth());
        assertNull(wrongUsernameFuncionario);
    }

}
