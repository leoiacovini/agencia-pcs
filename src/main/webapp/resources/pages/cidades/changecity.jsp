<%--
  Created by IntelliJ IDEA.
  User: scorpion
  Date: 23/11/16
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Alterar Cidade</title>
</head>
<body>
<form method="POST" action="/cidades/${requestScope.get("cidade").getId()}/edit">
    <table>
        <input type="hidden" name="Id"  value="${requestScope.get("cidade").getId()}">
        <tr><td>Nome:</td><td><input name="Nome" type="text" value="${requestScope.get("cidade").getNome()}"></td></tr>
        <tr><td>Estado:</td><td><input name="Estado" type="text" value="${requestScope.get("cidade").getEstado()}"></td></tr>
        <tr><td>País:</td><td><input name="Pais" type="text" value="${requestScope.get("cidade").getPais()}"></td></tr>
    </table>
    <tr><input value="Alterar" type="submit"></tr>
</form>
<form method="GET" action="/managercidades">
    <input value="Cancelar" type="submit">
</form>
<% if (request.getAttribute("invalido") != null)  { %>
<p><%=request.getAttribute("invalido").toString()%></p>
<% } %>
</body>
</html>
