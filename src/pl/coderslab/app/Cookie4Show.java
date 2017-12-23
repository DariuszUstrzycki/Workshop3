package pl.coderslab.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Cookie4Show")
public class Cookie4Show extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String HTML_START = "<html><body>";
	private static final String HTML_END = "</body></html>";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter(); //.append("Servlet Cookie4Del\n");
		out.append(HTML_START);
		out.append("Servlet Cookie4Show<br>");
		
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : cookies) {
				String name = c.getName();
				out.append("Cookie(" + name + ", " + c.getValue() + ") ")
					.append("<a href='/Workshop3/Cookie4Del?cookieName=")
					.append(name)
					.append("'>Click</a><br>");
			}
			out.append(HTML_END);
		} else {
			out.append("There are no cookies!");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
