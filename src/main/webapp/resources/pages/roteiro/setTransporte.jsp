<%@ page import="pcs.labsoft.agencia.models.Transporte" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: leoiacovini
  Date: 25/11/16
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Transporte> transportes = (List<Transporte>) request.getAttribute("transportes"); %>
<html>
<head>
    <title>Escolher Transporte</title>
</head>
<body>

<h2>Set Transporte</h2>

<form method="POST" action="/AgenciaPCS/roteiro/set-transporte">
    <select name="transporteId">
        <% for(Transporte transporte : transportes) { %>
        <option value="<%= transporte.getId() %>"> <%= transporte.getTipo() %> - <%= transporte.getPreco() %> </option>
        <% } %>
    </select>
    <input type="submit" name="Selecionar" />
</form>

</body>
</html>
