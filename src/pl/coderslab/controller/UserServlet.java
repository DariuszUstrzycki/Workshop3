package pl.coderslab.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.MySQLExerciseDao;
import pl.coderslab.dao.MySQLSolutionDao;
import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;


@WebServlet("/admin/users")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserDao userDao = new MySQLUserDao();
	private final SolutionDao solDao = new MySQLSolutionDao();
	private final ExerciseDao exlDao = new MySQLExerciseDao();
	private final String theView = "/views/users.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		/*case "create":
			showSolutionFormAndExercise(request,response);
			break;*/
		case "view":
			viewOneUser(request, response); 
			break;
		case "delete":
			deleteUser(request, response); 
			break;
		/*case "download":
			downloadAttachment(request, response);
			break;*/
		/*case "listForOneExercise":
			listForOneExercise(request, response); 
			break;*/
		case "list":
		default:
			listUsers(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
        if(action == null)
            action = "list";
        switch(action)
        {
            case "create":
               // createUser(request, response); - not implemented
                break;
            case "list":
            default:
                response.sendRedirect("users");
                break;
        }
	}
	
	private void listUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// getUsers - generateUsers
		List<User> allUsers = (List<User>) userDao.loadAllUsers();

		if (allUsers == null) {
			response.sendRedirect("users");
			return;
		} else {
			Collections.reverse(allUsers); // to improve
			request.setAttribute("allUsers", allUsers);
			request.getRequestDispatcher(theView).forward(request, response); // mozna forward
		}
	}
		// uruchamia sie po doPost?
		private void viewOneUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String idString = request.getParameter("userId");
			User user = getUser(idString, response); 
			if (user == null) {
				response.sendRedirect("users");
				return;
			} else {  
				request.setAttribute("oneUser", user); // przekazany zostanie parametr: ?action=view&solutionId=
				request.getRequestDispatcher(theView).forward(request, response); //preceded by sendredirect w doPost
			}
		}
		
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Long userId = null;
		try {
			userId = Long.parseLong(request.getParameter("userId"));
		} catch (NumberFormatException e) {
			response.sendRedirect("users");
			return;
		}
		
		boolean deleted = userDao.delete(userId);
		if (!deleted) { 
			response.sendRedirect("users");
			return;
		}
		
		response.sendRedirect("exercises" + "?action=list");
		
	}
		
		/**
		 * @return when null, redirects to "users"
		 */
		private User getUser(String idString, HttpServletResponse response) throws ServletException, IOException {

			if (idString == null || idString.length() == 0) {
				response.sendRedirect("users");
				return null;
			}

			try {
				User user = userDao.loadUserById(Integer.parseInt(idString));
				if (user == null) {
					response.sendRedirect("users");
					return null;
				}
				return user;

			} catch (Exception e) {
				response.sendRedirect("users");
				return null;
			}

		}

}
