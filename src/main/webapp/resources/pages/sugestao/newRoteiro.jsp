<%@ page import="pcs.labsoft.agencia.models.Cidade" %>
<%@ page import="java.util.List" %>
<%@ page import="pcs.labsoft.agencia.models.Cliente" %>
<%--
  Created by IntelliJ IDEA.
  User: scorpion
  Date: 01/12/16
  Time: 09:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% List<Cidade> cidades = (List<Cidade>) request.getAttribute("cidadesElegiveis"); %>
<% List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes"); %>
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
    <p>Cliente</p>
    <select name="clienteId">
        <% for(Cliente cliente : clientes) { %>
        <option value="<%= cliente.getId() %>"> <%= cliente.getNome() %> </option>
        <% } %>
    </select>
    <a href="/AgenciaPCS/clientes/registrar">Registrar Novo Cliente</a>
    <p>Número de Pessoas</p>
    <input type="number" name="numeroPessoas" />
    <br />
    <input type="submit" name="Selecionar" />
</form>


<form method="GET" action="/AgenciaPCS/">
    <input value="Voltar" type="submit">
</form>
<br />
<% if (request.getAttribute("EqualsCity") != null && request.getAttribute("EqualsCity").equals("Iguais") )  { %>
<p>Cidades origem e destino devem ser diferentes</p>
<% } %>

</body>
</html>
