package pl.coderslab.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({ "/home", "/" })
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(pl.coderslab.controller.Home.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("//////////Going through the servlet!");
		LOG.info("----------------Finally log is used----------------");
		

		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);;

	}

}
