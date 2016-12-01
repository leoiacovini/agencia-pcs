<%@ page import="pcs.labsoft.agencia.models.Roteiro" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: jhonata
  Date: 26/11/16
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Roteiro> roteiros = (List<Roteiro>) request.getAttribute("roteiros"); %>
<html>
<head>
    <title>Relatório de Vendas</title>



</head>
<body>
<% for(Roteiro roteiro:roteiros) { %>
<div style="border: solid medium black">
    <p>Id: <%= roteiro.getId() %></p>
    <p>Valor Total: R$<%= roteiro.getValor() %></p>
    <p>Numero de Trechos: <%= roteiro.getTrechos().size() %></p>
    <p>Funcionario: <%= roteiro.getFuncionario().getNome() %></p>
    <p>Cliente: <%= roteiro.getCliente().getNome() %></p>
</div>
<br />
<% } %>
<br />
<form method="GET" action="/AgenciaPCS/index">
    <input value="Voltar" type="submit">
</form>
</body>
</html>
