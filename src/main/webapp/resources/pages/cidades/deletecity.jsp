<%@ page import="pcs.labsoft.agencia.models.Cidade" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Excluir Cidade</title>
    <% List<Cidade> cidades = (List<Cidade>) request.getAttribute("cidades"); %>
</head>
<body>
<h3>Cidades disponíveis para remoção:</h3>
<form method="POST" name="/removercidade">
    <tr><select name="escolha">
        <option disabled selected value>Selecione uma opção</option>
        <% for (Cidade cidade : cidades) { %>
        <option value="<%=cidade.getId()%>"><%=cidade.getNome()%>
        </option>
        <% } %>
    </select></tr>
    <tr><input value="Deletar" type="submit"></tr>
</form>
</body>
</html>
