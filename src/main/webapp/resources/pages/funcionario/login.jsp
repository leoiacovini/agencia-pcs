<%--
  Created by IntelliJ IDEA.
  User: jhonata
  Date: 24/11/16
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h3>Agente, identifique-se</h3>
<form method="POST" name="login" action="/AgenciaPCS/login">
    <table>
        <tr><td>Nome de Usuário:</td><td><input name="username" type="text"></td></tr>
        <tr><td>Senha:</td><td><input name="password" type="password"></td></tr>
        <tr><td><input value="Login" type="submit"></td></tr>
    </table>
</form>
<form method="GET" action="/index">
    <input value="Voltar" type="submit">
</form>
</body>
</html>
