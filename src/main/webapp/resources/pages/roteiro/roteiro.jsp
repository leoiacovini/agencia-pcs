<%@ page import="pcs.labsoft.agencia.models.Cidade" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: scorpion
  Date: 18/11/16
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Cidade> cidades = (List<Cidade>) request.getAttribute("cidadesElegiveis"); %>
<html>
<head>
    <title>Montar Roteiro</title>
</head>
<body>
<h3>Montar Roteiro de viagem</h3>
<jsp:text>Selecione a cidade base:</jsp:text>

<%if(request.getSession().getAttribute("roteiro") == null) {%>
<form method="POST" action="/AgenciaPCS/roteiro">
    <select name="cidadeBaseId">
        <% for(Cidade cidade : cidades) { %>
            <option value="<%= cidade.getId() %>"> <%= cidade.getNome() %> </option>
        <% } %>
    </select>
    <input type="submit" name="Selecionar" />
</form>
<% }else{%>
<br>
<select>
    <option value="<%=request.getSession().getAttribute("roteiro") %>"></option>
</select>
<jsp:text>Tudo OK</jsp:text>
<%--<%=request.getAttribute("roteiro")%>--%>
<%}%>

</body>
</html>
