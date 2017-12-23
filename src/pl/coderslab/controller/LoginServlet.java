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
		
		Cookie alreadyLogged = findCookie("loggedUser", request);
		if (alreadyLogged != null) {
			response.sendRedirect("index.jsp");
		} else {
			response.sendRedirect("views/login.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("DoPost of LoginServlet called!");


		String email = request.getParameter("email");

		if (isValid(email, request.getParameter("password"))) {
			System.out.println("1. Valid email and password");

			UserDao dao = new MySQLUserDao();
			User user = dao.loadUserByEmail(email);
			
			if (user == null) {
				request.setAttribute("loginFailure", "Ooops, No email like this in the database!");
				getServletContext().getRequestDispatcher("/views/login.jsp").forward(request, response);
			} else {
				System.out.println("2. User is NOT null");
				boolean match = ENCODER.validatePassword(request.getParameter("password"), user.getPassword());
				
				if(match) {
					System.out.println("3. password matches");
					
					response.addCookie(new Cookie("loggedUser",user.getUsername()));
					
					////////////// ----------- czy ta czesc ma sens?
					// tylko teraz po co trzymac w sesji calego user'a
					/*HttpSession session = request.getSession();	    
			        session.setAttribute("loggedUser",user); */
			        /////////-------------
			        
					response.sendRedirect("index.jsp");
				} else {
					request.setAttribute("loginFailure", "<font color=red>Either email or password is wrong.</font>");
					getServletContext().getRequestDispatcher("/views/login.jsp").forward(request, response);
				}
			}
			
		} else {
			request.setAttribute("loginFailure", "Ooops, the email and password fields can't be empty!");
			getServletContext().getRequestDispatcher("/views/login.jsp").forward(request, response);
		}


	}
	
	/*private void upgradeUserStatus(HttpServletRequest request, HttpServletResponse response) {
		
		Cookie signedUp = findCookie("signedUp", request);
		
		if(signedUp != null) {
			signedUp.setMaxAge(0);
			response.addCookie(signedUp);
		} else {
			throw new IllegalStateException("The signedUp Cookie should not be null when user is logging!");
		}
				
		response.addCookie(new Cookie("loggedUser", "loggedUser"));
	}*/
	
	
	private boolean isValid(String email, String password) {
		return  ! (email == null || email.equals("") || password == null || password.equals(""));
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

	@Override
	public void destroy() {
		System.out.println("Destroy of loginServlet is called!");
	}

}
