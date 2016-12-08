<%@ page import="pcs.labsoft.agencia.models.Roteiro" %>
<%@ page import="pcs.labsoft.agencia.models.Trecho" %><%--
  Created by IntelliJ IDEA.
  User: scorpion
  Date: 08/12/16
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Roteiro roteiro = (Roteiro) request.getAttribute("roteiro"); %>
<html>
<head>
    <title>Roteiro Concluido</title>
</head>
<body>

<h2>Roteiro Concluido</h2>

<p>Valor Total: R$<%= roteiro.getValor() %></p>
<p>Duração Total: <%= roteiro.getDuracao() %> dias</p>

<div style="border: medium solid black;">
    <h4>Itinerario:</h4>
    <% for(Trecho trecho: roteiro.getTrechos()) {%>
    <div style="border: medium solid black;">
        <% if(trecho.isTrechoInicial()) { %>
        <h3>Trecho Inicial (Cidade Base)</h3>
        <% } %>
        <p>Cidade: <%= trecho.getCidade().getNome() %></p>
        <% if(trecho.getTransporte() != null) { %>
        <p>Transporte: <%= trecho.getTransporte().getTipo() %> de <%= trecho.getTransporte().getCidadeDePartida().getNome() %> para <%= trecho.getTransporte().getCidadeDeChegada().getNome() %></p>
        <% } %>
        <% if(trecho.getHotel() != null) { %>
        <p>Hotel: <%= trecho.getHotel().getNome() %></p>
        <% } %>
        <% if(trecho.getDuracao() != 0) { %>
        <p>Duração: <%= trecho.getDuracao() %></p>
        <% } %>
    </div>
    <% } %>
</div>
<br />

<form method="GET" action="/AgenciaPCS/">
    <input value="Voltar" type="submit">
</form>

</body>
</html>
