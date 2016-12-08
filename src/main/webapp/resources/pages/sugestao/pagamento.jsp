<%--
  Created by IntelliJ IDEA.
  User: scorpion
  Date: 08/12/16
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pagamento</title>
</head>
<body>

<h2>Pagamento</h2>

<form action="/AgenciaPCS/sugestao/concluir" method="POST">
    <p>Valor = <%= request.getAttribute("valor") %></p>
    <p>Forma</p>
    <select name="tipoPagamento">
        <option value="cartao">Cartão</option>
        <option value="dinheiro">Dinheiro</option>
    </select>
    <p>Codigo de Confirmação</p>
    <input type="text" name="codigoConfirmacao">
    <input type="submit" name="Selecionar">
</form>

</body>
</html>
