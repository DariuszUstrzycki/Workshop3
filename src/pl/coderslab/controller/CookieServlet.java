package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/cookies")
public class CookieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cookieParam = request.getParameter("cookies");
		
		System.out.println("CookieServlet called");
		
		if(cookieParam != null && cookieParam.equals("accept")) {
			Cookie c = new Cookie("cookieConsent", "accept");
			response.addCookie(c);
			System.out.println("Cookie created...");
		}
		
		response.sendRedirect(request.getContextPath() + "/home");
	}

}
