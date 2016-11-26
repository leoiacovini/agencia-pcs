<%@ page import="pcs.labsoft.agencia.models.Cidade" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: leoiacovini
  Date: 25/11/16
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Cidade> cidades = (List<Cidade>) request.getAttribute("proximasCidades"); %>
<html>
<head>
    <title>Proxima Cidade</title>
</head>
<body>

<h2>Proxima Cidade</h2>

<form method="POST" action="/AgenciaPCS/roteiro/set-proxima-cidade">
    <select name="proximaCidadeId">
        <% for(Cidade cidade : cidades) { %>
        <option value="<%= cidade.getId() %>"> <%= cidade.getNome() %> </option>
        <% } %>
    </select>
    <input type="submit" name="Selecionar" />
</form>

</body>
</html>
