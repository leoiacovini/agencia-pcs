<%@ page import="pcs.labsoft.agencia.models.Hotel" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: leoiacovini
  Date: 25/11/16
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Hotel> hoteis = (List<Hotel>) request.getAttribute("hoteis"); %>

<html>
<head>
    <title>Escolher Hotel</title>
</head>
<body>

<h2>Set Hotel</h2>

<form method="POST" action="/AgenciaPCS/roteiro/set-hotel">
    <select name="hotelId">
        <% for(Hotel hotel : hoteis) { %>
        <option value="<%= hotel.getID() %>"> <%= hotel.getNome() %> - <%= hotel.getPreco() %> </option>
        <% } %>
    </select>
    <p>Duração</p>
    <input type="number" name="duracao">
    <input type="submit" name="Selecionar" />
</form>

</body>
</html>
