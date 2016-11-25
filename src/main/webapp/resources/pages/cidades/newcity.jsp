<%--
  Created by IntelliJ IDEA.
  User: scorpion
  Date: 18/11/16
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nova Cidade</title>
</head>
<body>
<h3>Preencha os campos abaixo</h3>
<form method="POST" name="/novacidade" action="/novacidade">
<table>
    <tr><td>Nome:</td><td><input name="Nome" type="text"></td></tr>
    <tr><td>Estado:</td><td><input name="Estado" type="text"></td></tr>
    <tr><td>País:</td><td><input name="Pais" type="text"></td></tr>
    <tr><td><input value="Enviar" type="submit"></td></tr>
</table>
</form>
<form method="GET" action="/managercidades">
    <input value="Cancelar" type="submit">
</form>
</body>
</html>
