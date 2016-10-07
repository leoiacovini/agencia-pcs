<%@page import="pcs.labsoft.agencia.models.Hotel"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Hoteis</title>
</head>
<body>
	<h1>Lista de Hoteis</h1>
	<%
	List<Hotel> list = (List<Hotel>) request.getAttribute("list");
	
	for (Hotel hotel : list) {
	%>
	<li><a href="hoteis?id=<%hotel.getID(); %>" ><%=hotel.getID() %> | <%=hotel.getNome() %></a></li>
	
	<%} %>
</body>
</html>
