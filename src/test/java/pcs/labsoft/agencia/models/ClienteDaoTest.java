package pcs.labsoft.agencia.models;

import org.junit.Test;
import pcs.labsoft.agencia.aux.SystemTest;
import pcs.labsoft.agencia.models.dao.ClienteDao;
import static org.junit.Assert.*;

/**
 * Created by leoiacovini on 24/11/16.
 */
public class ClienteDaoTest extends SystemTest {

    @Test
    public void createCliente() {
        ClienteDao dao =  new ClienteDao(system.getDataBase());
        Cliente cliente = new Cliente("Cliente 1", "cpf_1234", "rg_1234", "cliente@email.com", "passaporte_1234", "telefone_1234");
        Cliente createdCliente = dao.createCliente(cliente);
        assertNotNull(createdCliente);
        assertEquals(cliente.getNome(), createdCliente.getNome());
        assertEquals(cliente.getCpf(), createdCliente.getCpf());
        assertEquals(cliente.getEmail(), createdCliente.getEmail());

        Cliente notValid = dao.createCliente(cliente);
        assertNull("we can't create more than one Cliente with the same CPF or RG", notValid);
    }

    @Test
    public void getClienteById() {
        ClienteDao dao =  new ClienteDao(system.getDataBase());
        Cliente cliente = new Cliente("Cliente 1", "cpf_1234", "rg_1234", "cliente@email.com", "passaporte_1234", "telefone_1234");
        Cliente createdCliente = dao.createCliente(cliente);
        assertNotNull(createdCliente);
        Cliente foundCliente = dao.getClienteById(createdCliente.getId());
        assertNotNull(foundCliente);
        assertEquals(createdCliente.getEmail(), foundCliente.getEmail());
        assertEquals(createdCliente.getCpf(), foundCliente.getCpf());
        assertEquals(createdCliente.getRg(), foundCliente.getRg());
    }

}
