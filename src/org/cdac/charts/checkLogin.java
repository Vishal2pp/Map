package org.cdac.charts;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/checkLogin")
public class checkLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		ServletContext context = getServletContext();
		AMChartsConfigurator amc = (AMChartsConfigurator) context.getAttribute("configuration");
		
		HttpSession session = request.getSession();
		session.setAttribute("u_name", null);
		session.setAttribute("u_password", null);
		String name = (String) request.getParameter("u_name");
		String pwd = (String) request.getParameter("u_password");
		
		if(name.equals(amc.getUsername()) && pwd.equals(amc.getPassword()))
		{
			session.setAttribute("u_name", name);
			session.setAttribute("u_password", pwd);
			response.sendRedirect("FileUpload.jsp");
		}
		else
		{
			response.sendRedirect("index.jsp");
		}
	}

	

}
