<%@ page import="pcs.labsoft.agencia.models.Hotel" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: leoiacovini
  Date: 09/12/16
  Time: 01:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% List<Hotel> hoteis = (List<Hotel>) request.getAttribute("hoteis"); %>
    <% int trechoIndex = (int) request.getAttribute("trechoIndex"); %>
    <title>Update Hotel</title>
</head>
<body>

<form method="POST" action="/AgenciaPCS/roteiro/updateHotel/<%= trechoIndex %>">
    <select name="hotelId">
        <% for(Hotel hotel : hoteis) { %>
        <option value="<%= hotel.getID() %>"> <%= hotel.getNome() %> - <%= hotel.getPreco() %> </option>
        <% } %>
    </select>
    <input type="submit" name="Selecionar" />
</form>

</body>
</html>
