<%@ page import="pcs.labsoft.agencia.models.Cidade" %>
<%@ page import="java.util.List" %>
<%@ page import="pcs.labsoft.agencia.models.Cliente" %><%--
  Created by IntelliJ IDEA.
  User: scorpion
  Date: 18/11/16
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Cidade> cidades = (List<Cidade>) request.getAttribute("cidadesElegiveis"); %>
<% List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes"); %>
<html>
<head>
    <title>Montar Roteiro</title>
</head>
<body>
<h3>Montar Roteiro de viagem</h3>
<form method="POST" action="/AgenciaPCS/roteiro/new">
    <p>Escolha a cidade de origem</p>
    <select name="cidadeBaseId">
        <% for(Cidade cidade : cidades) { %>
        <option value="<%= cidade.getId() %>"> <%= cidade.getNome() %> </option>
        <% } %>
    </select>
    <p>Cliente</p>
    <select name="clienteId">
        <% for(Cliente cliente : clientes) { %>
        <option value="<%= cliente.getId() %>"> <%= cliente.getNome() %> </option>
        <% } %>
    </select>
    <p>Número de Pessoas</p>
    <input type="number" name="numeroPessoas"/>
    <a href="/AgenciaPCS/clientes/registrar">Registrar Novo Cliente</a>
    <br />
    <br />
    <input type="submit" name="Selecionar" />
</form>


</body>
</html>
