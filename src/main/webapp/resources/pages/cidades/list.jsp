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
    <h3>Cidades cadastradas (<%= cidades.size() %>)</h3>
    <% for (Cidade cidade : cidades) { %>
    <a href="/cidades/<%= cidade.getId() %>">
        <li><%= cidade %></li>
    </a>
    <% } %>
</ul>
<form method="GET" action="/managercidades">
    <input value="Voltar" type="submit">
</form>

</body>
</html>
