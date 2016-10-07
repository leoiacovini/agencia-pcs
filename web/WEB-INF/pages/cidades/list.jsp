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
    <a href="cidades?id=<%= cidade.getId() %>">
        <li><%= cidade %></li>
    </a>
    <% } %>
</ul>

</body>
</html>
