package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interceptors.AgenteRequired;
import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.Cliente;
import pcs.labsoft.agencia.models.Funcionario;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by leoiacovini on 23/11/16.
 */
public class AuthController extends HttpController {

    @HttpHandler(path = "/login", method = "GET")
    public void getLoginFuncionario(HttpRequest request, HttpServletResponse response) {
        try {
            renderLogin(request).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @HttpHandler(path = "/login", method = "POST")
    public void loginFuncionario(HttpRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username != null && password != null) {
            Funcionario funcionario = Funcionario.logIn(username, password, db, auth);
            if (funcionario != null) {
                request.getSession().setAttribute("user", funcionario);
                response.getWriter().write("Logado com sucesso: " + funcionario.getNome());
            } else {
                response.getWriter().write("Login Invalido");
            }
        }
    }

    @HttpHandler(path = "/clientes/registrar", method = "GET", interceptors = {AgenteRequired.class})
    public void renderRegistrarCliente(HttpRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("clientes/register.jsp").forward(request, response);
    }

    @HttpHandler(path = "/clientes/registrar", method = "POST", interceptors = {AgenteRequired.class})
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

    private RequestDispatcher renderLogin(HttpRequest servletRequest) {
        return servletRequest.getRequestDispatcher("funcionario/login.jsp");
    }
}
