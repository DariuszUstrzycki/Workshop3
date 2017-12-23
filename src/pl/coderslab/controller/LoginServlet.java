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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final PasswordEncoder ENCODER = new PasswordEncoder();


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//getServletContext().getRequestDispatcher("/views/login.jsp").forward(request, response);
		System.out.println("DoGet of LoginServlet called!");
		
		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("loggedUser");
		
		if (loggedUser != null) {
			response.sendRedirect("home");
		} else {
			response.sendRedirect("views/login.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
																								System.out.println("DoPost of LoginServlet called!");
		
		String email = request.getParameter("email");

		if (notNullOrEmpty(email, request.getParameter("password"))) {
																								System.out.println("1. not null/empty email and password");

			UserDao dao = new MySQLUserDao();
			User user = dao.loadUserByEmail(email);
			
			if (user == null) {
				request.setAttribute("loginFailure", "Ooops, No email like this in the database!");
				getServletContext().getRequestDispatcher("/views/login.jsp").forward(request, response);
			} else {
																									System.out.println("2. User is NOT null. Checking if passwords match...");
				boolean match = ENCODER.validatePassword(request.getParameter("password"), user.getPassword());
				
				if(match) {
																									System.out.println("3. passwords match!");
					HttpSession session = request.getSession();
					session.setAttribute("loggedUser", user); // dodany z haslem
			        																					System.out.println("Logged user added to session. Redirect to home");
					response.sendRedirect("home");
				} else {
																											System.out.println(" passwords  dont  match! forward to login");
					request.setAttribute("loginFailure", "<font color=red>Either email or password is wrong.</font>");
					getServletContext().getRequestDispatcher("/views/login.jsp").forward(request, response);
				}
			}
			
		} else {
																										System.out.println("Incorrect login. forward to login.jsp");
			request.setAttribute("loginFailure", "Ooops, the email and password fields can't be empty!");
			getServletContext().getRequestDispatcher("/views/login.jsp").forward(request, response);
		}


	}
	
	
	private boolean notNullOrEmpty(String email, String password) {
		return  ! (email == null || email.equals("") || password == null || password.equals(""));
	}


}
