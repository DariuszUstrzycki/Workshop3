package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.app.PasswordEncoder;
import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final PasswordEncoder ENCODER = new PasswordEncoder();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("loggedUser");

		if (loggedUser == null) {
			response.sendRedirect(request.getContextPath() + "/views/login.jsp");
		} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");

		if (nullOrEmpty(email, request.getParameter("password"))) {
			forwardToLoginPage(request, response, "Ooops, the email/password fields can't be empty!");
			return;
		}

		UserDao dao = new MySQLUserDao();
		User user = dao.loadUserByEmail(email);

		if (user == null) {
			forwardToLoginPage(request, response, "Ooops, no email like this in the database!");
			return;
		}

		boolean match = ENCODER.validatePassword(request.getParameter("password"), user.getPassword());

		if (match) {
			request.getSession().setAttribute("loggedUser", user); // dodany z haslem
			
			if(request.getParameter("remember") != null) {
				rememberUser(request, response, user.getId());
			}
			response.sendRedirect(request.getContextPath() + "/home");
			
		} else {
			forwardToLoginPage(request, response, "Either email or password is wrong.");
		}
	}

	private void forwardToLoginPage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute("loginFailure", "<font color=red>" + message + "</font>");

		if (!response.isCommitted()) {
			getServletContext().getRequestDispatcher("/views/login.jsp").forward(request, response);
		}
	}

	private boolean nullOrEmpty(String email, String password) {
		return (email == null || email.equals("") || password == null || password.equals(""));
	}
	
	private void rememberUser(HttpServletRequest request,HttpServletResponse response, long userId) throws IOException {
			Cookie c = new Cookie("remember", String.valueOf(userId));
			c.setMaxAge(60*60*24*360);
			response.addCookie(c);
	}

}
