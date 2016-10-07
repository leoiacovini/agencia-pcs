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

</body>
</html>
