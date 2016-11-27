<%--
  Created by IntelliJ IDEA.
  User: leoiacovini
  Date: 25/11/16
  Time: 01:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrar Cliente</title>
</head>
<body>
    <h2>Registrar novo cliente</h2>
    <br />
    <form method="POST">
        <table>
            <tr><td>Nome</td><td><input name="nome" type="text"></td></tr>
            <tr><td>CPF:</td><td><input name="cpf" type="text"></td></tr>
            <tr><td>RG:</td><td><input name="rg" type="text"></td></tr>
            <tr><td>Email:</td><td><input name="email" type="text"></td></tr>
            <tr><td>Passaporte:</td><td><input name="passaporte" type="text"></td></tr>
            <tr><td>Telefone:</td><td><input name="telefone" type="text"></td></tr>
            <tr><td><input value="Cadastrar" type="submit"></td></tr>
        </table>
    </form>

</body>
</html>
