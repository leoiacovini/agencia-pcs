package pcs.labsoft.agencia.controllers;

import pcs.labsoft.agencia.components.interfaces.HttpController;
import pcs.labsoft.agencia.misc.HttpHandler;
import pcs.labsoft.agencia.misc.HttpRequest;
import pcs.labsoft.agencia.models.Funcionario;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

}
