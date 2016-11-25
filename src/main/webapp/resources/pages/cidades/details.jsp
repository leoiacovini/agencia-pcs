<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cidade</title>
</head>
<body>

<p>Id: ${requestScope.get("cidade").getId()}</p>
<p>Nome: ${requestScope.get("cidade").getNome()} </p>
<p>Estado: ${requestScope.get("cidade").getEstado()}</p>
<p>Pais: ${requestScope.get("cidade").getPais()}</p>
<form method="POST" action="/AgenciaPCS/cidades/${requestScope.get("cidade").getId()}/delete">
    <tr><input value="Deletar" type="submit"></tr>
</form>
<form method="GET" action="/AgenciaPCS/cidades/${requestScope.get("cidade").getId()}/edit">
    <tr><input value="Alterar" type="submit"></tr>
</form>
<form method="GET" action="/AgenciaPCS/managercidades">
    <input value="Voltar" type="submit">
</form>
</body>
</html>
