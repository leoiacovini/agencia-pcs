<%--
  Created by IntelliJ IDEA.
  User: jhonata
  Date: 25/11/16
  Time: 08:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Agência</title>
</head>
<body>
    <h2>Agência de viagens PCS</h2>
    <ul>
        <a href="/AgenciaPCS/login"><li>Fazer login</li></a>
        <a href="/AgenciaPCS/managercidades"><li>Gerenciar cidades</li></a>
        <a href="/AgenciaPCS/roteiro/new"><li>Montar Roteiro</li></a>
        <a href="/AgenciaPCS/sugestao/roteiro/new"><li>Sugestão de Roteiro</li></a>
        <a href="/AgenciaPCS/relatorio/vendas"><li>Relatório de vendas</li></a>
    </ul>

    <% if (request.getAttribute("login").equals("ok") | request.getAttribute("login").equals("nok"))  { %>
    <%=request.getAttribute("msg")%>
    <% } %>
</body>
</html>
