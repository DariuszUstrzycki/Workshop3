package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
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

@WebServlet(
		urlPatterns = { "/signup" }, 
		initParams = { 
				@WebInitParam(name = "defaultUserGroup", value = "1", description = "by defaulty user is added to database as member of the student group")
		})
public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static int userGroup; 
	private static final PasswordEncoder ENCODER = new PasswordEncoder();

	@Override
	public void init() throws ServletException {
		super.init();
		userGroup = Integer.parseInt(getInitParameter("defaultUserGroup"));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
																			System.out.println("SignUpServlet enter via doGet!!!");
		 
		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("loggedUser");
		
		if (loggedUser != null) {
			response.sendRedirect("home");
		} else {
			response.sendRedirect("views/signup.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("loggedUser");
		
		if (loggedUser != null) {
																				System.out.println("SignUpServlet - Logged user exists - redirect to home");
			response.sendRedirect("home");
		} else {
		 // sign up only if no logged user

			String userName = request.getParameter("username");
			String email = request.getParameter("email");

			if (isValid(userName, email, request.getParameter("password"))) {

				UserDao dao = new MySQLUserDao();
				int id = dao.save(new User(userName, email, ENCODER.encode(request.getParameter("password")), userGroup));

				if (id > 0) {
																					System.out.println("SignUpServlet - User added to db - redirect to home");
					session.setAttribute("loggedUser", new User(userName, email, "", userGroup));
					response.sendRedirect("home");
				}

			} else {
																			System.out.println("SignUpServlet: incorrect signup - forward to signup.jsp");
				request.setAttribute("signupFailure", "The data you provided is incomplete. Please try again!");
				getServletContext().getRequestDispatcher("/views/signup.jsp").forward(request, response);
			}

		}
	}

	private boolean isValid(String username, String email, String password) {
		return !(username == null || username.equals("") || email == null || email.equals("") || password == null
				|| password.equals(""));
	}
	
}
