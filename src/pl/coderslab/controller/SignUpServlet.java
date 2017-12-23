package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.app.PasswordEncoder;
import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_USER_GROUP_STUDENT = 1; // move to web.xml
	private static final PasswordEncoder ENCODER = new PasswordEncoder();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Cookie alreadyLogged = findCookie("loggedUser", request);
		if (alreadyLogged != null) {
			response.sendRedirect("index.jsp");
		} else {
			response.sendRedirect("views/signup.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie alreadyLogged = findCookie("loggedUser", request);
		
		if (alreadyLogged != null) {
			response.sendRedirect("index.jsp");
		} else { // sign up only if no logged user

			String userName = request.getParameter("username");
			String email = request.getParameter("email");

			if (isValid(userName, email, request.getParameter("password"))) {

				UserDao dao = new MySQLUserDao();
				int id = dao.save(new User(userName, email, ENCODER.encode(request.getParameter("password")),
						DEFAULT_USER_GROUP_STUDENT));

				if (id > 0) {
					response.addCookie(new Cookie("loggedUser", userName));
					request.setAttribute("message", "Registration very successful!");
					
					//request.getRequestDispatcher("/index.jsp").forward(request, response); - tez działa
					//getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
					// The index.jsp IS NOT REFRESHED PROPERLY - DOESN'T DETECT THE LOGGEDuSER cOOKIE :
					// QUESTION: czy moge zrobic redirect i przekazac attribute? mysle ze tak!
					response.sendRedirect("index.jsp");
				}

			} else {
				request.setAttribute("signupFailure", "The data you provided is incomplete. Please try again!");
				doGet(request, response);
			}

		}
	}

	private boolean isValid(String username, String email, String password) {
		return !(username == null || username.equals("") || email == null || email.equals("") || password == null
				|| password.equals(""));
	}

	private Cookie findCookie(String cookieName, HttpServletRequest request) {

		if (cookieName != null && !cookieName.equals("")) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (cookieName.equals(c.getName())) {
						return c;
					}
				}
			}

		}

		return null;

	}

}
