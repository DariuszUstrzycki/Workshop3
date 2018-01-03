package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

@WebServlet("/deleteuser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User user = null;

		if (session != null) {

			user = (User) session.getAttribute("loggedUser");

			if (user != null) {

				UserDao dao = new MySQLUserDao();
				boolean success = dao.delete(user.getId());

				if (success) {
					
					session.invalidate();
					
					//remove rememberMeCookie on logout
					Cookie userLoginData = findCookie("remember", request);
					if(userLoginData != null) {
						userLoginData.setMaxAge(0);
						response.addCookie(userLoginData);
					}
					
					response.sendRedirect(request.getContextPath() + "/home");
				} else {
					// missing message for the user if updat not possible!
				}
			}
		}

	}
	
	private Cookie findCookie(String cookieName, HttpServletRequest request) {

		if(cookieName != null && !cookieName.equals("")) {
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
