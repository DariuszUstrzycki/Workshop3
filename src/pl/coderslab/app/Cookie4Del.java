package pl.coderslab.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Cookie4Del")
public class Cookie4Del extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String cookieName = request.getParameter("cookieName");
		
		if(cookieName != null) {
			Cookie toDelete = findCookie(cookieName, request);
			
			if(toDelete != null) {
				toDelete.setMaxAge(0);
				response.addCookie(toDelete);
				out.println("Cookie " + cookieName + ", " + toDelete.getValue() + " has been removed!");
			} else {
				out.println("No cookies to delete!");
			}
		} else {
			out.println("No cookies to delete!");
		}
		
	}
	
	private Cookie findCookie(String cookieName, HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				if (cookieName.equals(c.getName())) {
					return c;
				}
			}
		}

		return null;
	}

	
	

}
