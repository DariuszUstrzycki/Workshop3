package pl.coderslab.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.app.PasswordEncoder;
import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/updateuser")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final PasswordEncoder ENCODER = new PasswordEncoder();   
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}
		
		User loggedUser = (User) session.getAttribute("loggedUser");
		
		if(loggedUser == null) {
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}
		
		//request.setAttribute("userId", loggedUser.getId());
		getServletContext().getRequestDispatcher("/views/updateUser.jsp").forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			
			System.out.println("---------------------------------UPDATE-----------------------------------------------------------");

			HttpSession session = request.getSession();
			User loggedUser = (User) session.getAttribute("loggedUser");
			
			if (loggedUser == null) {
				response.sendRedirect(request.getContextPath() + "/login");      							System.out.println("Cannot update because logged user info is missing");
			} else {
			
				
				String updatedEmail = request.getParameter("email");
				
				System.out.println("Entering the update...");

				if (nullOrEmpty(updatedEmail, request.getParameter("password"))) {
					forwardToUpdatePage(request, response,  "The data you provided is incomplete. Please try again!");
					System.out.println("1. NULL or EMPTY found");
					return;
				} 
				
				if(! updatedEmail.equals(loggedUser.getEmail())) {
					if(!isUniqueEmail(updatedEmail)) {
						System.out.println("<<<<<Not a unique email!!! >>>>>");
						forwardToUpdatePage(request, response,  "There's already an email like this!");
						System.out.println("2A. code reached after forward");
						return;
					}
				}
				
				System.out.println("The construction of a new user");
				
				User updatedUser = new User();
				//set uneditable data
				updatedUser.setId(loggedUser.getId());
				updatedUser.setUsername(loggedUser.getUsername());
				updatedUser.setUserGroupId(loggedUser.getUserGroupId());
				//set possibly updated data
				updatedUser.setEmail(updatedEmail);
				updatedUser.setPassword(ENCODER.encode(request.getParameter("password")));

				boolean success = updateInDatabase( updatedUser, request); 

				if (success) {
																			System.out.println("Success. The user has been updated!");
					session.setAttribute("loggedUser", updatedUser);
					session.setAttribute("successMessage", "Your data has been updated");
					if(!response.isCommitted())
						response.sendRedirect(request.getContextPath() + "/home");
				} else {
																		System.out.println("Failure. The user has NOT been updated!");
				}

			}
		}

		private boolean nullOrEmpty(String email, String password) {
			return ( email == null || email.equals("") || password == null 	|| password.equals(""));
		}
		
		
		private void forwardToUpdatePage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
			request.setAttribute("updateFailure", message);
			if(!response.isCommitted()) {
				System.out.println("forwarding called!");
				getServletContext().getRequestDispatcher("/views/updateUser.jsp").forward(request, response);
			}
		}
		
		
		/**
		 * @return id of the persisted element
		 */
		private boolean updateInDatabase(User updatedUser, HttpServletRequest request) {
			System.out.println(">>>>>>>>updated in db attempted<<<<<<<<<");
			UserDao dao = new MySQLUserDao();
			boolean success = dao.update(updatedUser);
			System.out.println("DaoUPDATE returns" + success) ;
			return success;
		}
		
		private boolean isUniqueEmail(String email) {
			UserDao dao = new MySQLUserDao();
			return ( dao.loadUserByEmail(email) == null ) ? true : false;
		}
		
		private boolean isUniqueUsername(String username) {
			UserDao dao = new MySQLUserDao();
			return ( dao.loadUserByUsername(username) == null ) ? true : false;
		}
		
	}
