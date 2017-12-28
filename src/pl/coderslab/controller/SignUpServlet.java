package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.app.PasswordEncoder;
import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

@WebServlet(urlPatterns = { "/signup" }, initParams = {
		@WebInitParam(name = "defaultUserGroup", value = "1", description = "by defaulty user is added to database as member of the student group") })
public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private int userGroup = 1;
	private String adminLogin = "admin";
	private String adminInitialPass = "qwe";
	private static final PasswordEncoder ENCODER = new PasswordEncoder();

	@Override
	public void init() throws ServletException {
		super.init();

		Integer userGroupParam = Integer.parseInt(getInitParameter("defaultUserGroup"));
		if (userGroupParam != null) {
			userGroup = userGroupParam;
		}

		String adminLoginParam = getServletContext().getInitParameter("adminLogin");
		if (adminLoginParam != null) {
			adminLogin = adminLoginParam;
		}

		String initPassParam = getServletContext().getInitParameter("adminPass");
		if (initPassParam != null) {
			adminInitialPass = initPassParam;
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("loggedUser");

		if (loggedUser != null) {
			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			response.sendRedirect(request.getContextPath() + "/views/signup.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("loggedUser");

		if (loggedUser != null) {
			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			// sign up only if no logged user

			String userName = request.getParameter("username");
			String email = request.getParameter("email");

			if (nullOrEmpty(userName, email, request.getParameter("password"))) {
				forwardToSignUpPage(request, response, "The data you provided is incomplete. Please try again!");
				return;
			}

			if (!isUniqueEmail(email)) {
				forwardToSignUpPage(request, response, "There's already an email like this!");
				return;
			}

			if (!isUniqueUsername(userName)) {
				forwardToSignUpPage(request, response, "There's already a username like this!");
				return;
			}

			// unauthorized access as admin
			if (adminLogin.equals(userName) && !adminInitialPass.equals(request.getParameter("password"))) {
				forwardToSignUpPage(request, response, "Please provide correct data to sign up as the administrator!");
				return;
			}

			long id = addToDatabase(userName, email, request);

			if (id > 0) {
				session.setAttribute("loggedUser", new User(id, userName, email, "", userGroup));
				if (!response.isCommitted())
					response.sendRedirect(request.getContextPath() + "/home");
			}

		}
	}

	private boolean nullOrEmpty(String username, String email, String password) {
		return (username == null || username.equals("") || email == null || email.equals("") || password == null
				|| password.equals(""));
	}

	private void forwardToSignUpPage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute("signupFailure", message);
		if (!response.isCommitted()) {
			getServletContext().getRequestDispatcher("/views/signup.jsp").forward(request, response);
		}
	}

	/**
	 * @return id of the persisted element
	 */
	private int addToDatabase(String userName, String email, HttpServletRequest request) {
		UserDao dao = new MySQLUserDao();
		return dao.save(new User(userName, email, ENCODER.encode(request.getParameter("password")), userGroup));
	}

	private boolean isUniqueEmail(String email) {
		UserDao dao = new MySQLUserDao();
		return (dao.loadUserByEmail(email) == null) ? true : false;
	}

	private boolean isUniqueUsername(String username) {
		UserDao dao = new MySQLUserDao();
		return (dao.loadUserByUsername(username) == null) ? true : false;
	}

}
