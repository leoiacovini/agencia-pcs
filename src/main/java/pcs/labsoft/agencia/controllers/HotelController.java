package pcs.labsoft.agencia.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pcs.labsoft.agencia.models.Hotel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HotelController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		List<Hotel> list = new ArrayList<Hotel>();
		
		if (request.getParameter("id") != null)
		{
			RequestDispatcher r = request.getRequestDispatcher("src/main/webapp/WEB-INF/pages/hoteis/details.jsp");
			r.forward(request, response);
		}
		else 
		{
			request.setAttribute("list", list);
			RequestDispatcher r = request.getRequestDispatcher("src/main/webapp/WEB-INF/pages/hoteis/list.jsp");
			r.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
