<%@ page import="pcs.labsoft.agencia.models.Cidade" %>
<%@ page import="java.util.List" %>
<%@ page import="pcs.labsoft.agencia.models.Roteiro" %>
<%@ page import="pcs.labsoft.agencia.models.Trecho" %><%--
  Created by IntelliJ IDEA.
  User: leoiacovini
  Date: 25/11/16
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Roteiro roteiro = (Roteiro) request.getAttribute("roteiro"); %>
<html>
<head>
    <title>Novo Roteiro</title>
</head>
<body>
<h3>Montar Roteiro de viagem</h3>

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

<br />
<% } %>

<a href="/AgenciaPCS/roteiro/get-proxima-cidade"> Novo Trecho </a>
<br/>
<a href="/AgenciaPCS/roteiro/pagamento">Finalizar</a>

</body>
</html>
