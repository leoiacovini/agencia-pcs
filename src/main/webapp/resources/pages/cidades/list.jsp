<%@ page import="pcs.labsoft.agencia.models.Cidade" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lista de Cidades</title>
    <% List<Cidade> cidades = (List<Cidade>) request.getAttribute("cidades"); %>
</head>
<body>

<ul>
    <% for (Cidade cidade : cidades) { %>
    <a href="/cidades/<%= cidade.getId() %>">
        <li><%= cidade %></li>
    </a>
    <% } %>
    <a href="/removercidade">
        <li>Excluir cidade</li>
    </a>
    <a href="/mudarcidade">
        <li>Alterar Cidade</li>
    </a>
</ul>

</body>
</html>
