<%--
  Created by IntelliJ IDEA.
  User: scorpion
  Date: 18/11/16
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gerenciador de Cidades</title>
</head>
<body>
    <h3>Escolha uma das opções abaixo</h3>

    <ul>
        <a href="/AgenciaPCS/cidades/">
            <li>Listar cidade</li>
        </a>
        <a href="/AgenciaPCS/novacidade">
            <li>Criar cidade</li>
        </a>

    </ul>

    <% if (request.getAttribute("Adicao").equals("OK"))  { %>
       <jsp:text>Cidade criada com sucesso</jsp:text>
    <% } %>
    <% if (request.getAttribute("Remocao").equals("OK"))  { %>
    <jsp:text>Cidade removida com sucesso</jsp:text>
    <% } %>
    <% if (request.getAttribute("Edicao").equals("OK"))  { %>
    <jsp:text>Cidade alterada com sucesso</jsp:text>
    <% } %>
    </body>
</html>
