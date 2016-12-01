<%@ page import="pcs.labsoft.agencia.models.Cidade" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: scorpion
  Date: 01/12/16
  Time: 09:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% List<Cidade> cidades = (List<Cidade>) request.getAttribute("cidadesElegiveis"); %>
<html>
<head>
    <title>Sugerir Roteiro</title>
</head>
<body>
<h3>Sugestão de Roteiro</h3>


<form method="POST" action="/AgenciaPCS/sugestao/roteiro/new">
    <p>Escolha a cidade de origem</p>
    <select name="cidadeBaseId">
        <% for(Cidade cidade : cidades) { %>
        <option value="<%= cidade.getId() %>"> <%= cidade.getNome() %> </option>
        <% } %>
    </select>
    <br />
    <p>Escolha a cidade de destino</p>
    <select name="cidadeFinalId">
        <% for(Cidade cidade : cidades) { %>
        <option value="<%= cidade.getId() %>"> <%= cidade.getNome() %> </option>
        <% } %>
    </select>
    <br />
    <input type="submit" name="Selecionar" />
</form>


<form method="GET" action="/AgenciaPCS/index">
    <input value="Voltar" type="submit">
</form>

</body>
</html>
