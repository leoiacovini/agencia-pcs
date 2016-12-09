<%@ page import="pcs.labsoft.agencia.models.Roteiro" %>
<%@ page import="pcs.labsoft.agencia.models.Trecho" %><%--
  Created by IntelliJ IDEA.
  User: scorpion
  Date: 08/12/16
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Roteiro roteiro = (Roteiro) request.getAttribute("roteiro"); %>
<html>
<head>
    <title>Roteiro sugerido</title>
</head>
<body>
<h3>Roteiro de viagem sugerido</h3>
<% int index = 0; %>
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
        <a href="/AgenciaPCS/sugestao/updateHotel/<%= index %>">Alterar Hotel</a>
    <% } %>
    <% if(trecho.getDuracao() != 0) { %>
        <p>Duração: <%= trecho.getDuracao() %></p>
    <% } %>
</div>
<% index++;  %>
<br />
<% } %>
<br/>
<a href="/AgenciaPCS/sugestao/pagamento">Finalizar</a>

</body>
</html>
