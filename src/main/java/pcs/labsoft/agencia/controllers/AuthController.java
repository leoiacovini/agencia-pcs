package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interceptors.AgenteRequired;
import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.Cliente;
import pcs.labsoft.agencia.models.Funcionario;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by leoiacovini on 23/11/16.
 */
public class AuthController extends HttpController {

    @HttpHandler(path = "/login", method = "GET")
    public void getLoginFuncionario(HttpRequest request, HttpServletResponse response) {

    }

    @HttpHandler(path = "/login", method = "POST")
    public void loginFuncionario(HttpRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username != null && password != null) {
            String hashedPassword = auth.secureHash(password);
            Funcionario funcionario = Funcionario.logIn(username, hashedPassword, db);
            if (funcionario != null) {
                request.getSession().setAttribute("user", funcionario);
                response.getWriter().write("Logado com sucesso: " + funcionario.getNome());
            } else {
                response.getWriter().write("Login Invalido");
            }
        }
    }

    @HttpHandler(path = "/cliente", method = "POST", interceptors = {AgenteRequired.class})
    public void registerCliente(HttpRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter("nome");
        String rg = request.getParameter("rg");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String passaporte = request.getParameter("passaporte");
        Cliente newCliente = Cliente.registerCliente(new Cliente(nome, cpf, rg, email, passaporte, telefone), db);
        if (newCliente != null) {
            response.getWriter().write("Cliente cadastrado com sucesso: " + newCliente.getEmail() + " - " + newCliente.getId());
        }
    }

}
