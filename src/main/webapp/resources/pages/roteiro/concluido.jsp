<%@ page import="pcs.labsoft.agencia.models.Roteiro" %>
<%@ page import="pcs.labsoft.agencia.models.Trecho" %><%--
  Created by IntelliJ IDEA.
  User: leoiacovini
  Date: 26/11/16
  Time: 21:53
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
    <h4>Iteinerario:</h4>
    <% for(Trecho trecho: roteiro.getTrechos()) {%>
    <div style="border: medium solid black;">
        <% if(trecho.isTrechoInicial()) { %>
        <h3>Trecho Inicial (Cidade Base)</h3>
        <% } %>
        <p>Cidade: <%= trecho.getCidade().getNome() %></p>
        <p>Transporte: <%= trecho.getTransporte().getTipo() %> para <%= trecho.getTransporte().getCidadeDeChegada().getNome() %></p>
        <p>Hotel: <%= trecho.getHotel() != null ? trecho.getHotel().getNome() : "Nenhum" %></p>
        <p>Duração: <%= trecho.getDuracao() %></p>
    </div>
    <% } %>
</div>
<br />

</body>
</html>