package pl.coderslab.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.MySQLUserGroupDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.dao.UserGroupDao;
import pl.coderslab.model.UserGroup;


@WebServlet("/groups")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserDao userDao = new MySQLUserDao();
	private final UserGroupDao groupDao = new MySQLUserGroupDao();
	private final String theView = "/views/groups.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		/*case "create":
			showSolutionFormAndExercise(request,response);
			break;*/
		case "view":
			viewOneGroup(request, response); 
			break;
		/*case "download":
			downloadAttachment(request, response);
			break;*/
		/*case "listForOneExercise":
			listForOneExercise(request, response); 
			break;*/
		case "list":
		default:
			listGroups(request, response);
		}
		
	}

	
	private void listGroups(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// getGroups - generateGroups
		List<UserGroup> allGroups = (List<UserGroup>) groupDao.loadAllUserGroups();

		if (allGroups == null) {
			response.sendRedirect("groups");
			return;
		} else {
			Collections.reverse(allGroups); // to improve
			request.setAttribute("allGroups", allGroups);
			request.getRequestDispatcher(theView).forward(request, response); // mozna forward
		}
	}
		// uruchamia sie po doPost?
		private void viewOneGroup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String idString = request.getParameter("groupId");
			UserGroup group = getGroup(idString, response); 
			if (group == null) {
				response.sendRedirect("groups");
				return;
			} else {  
				request.setAttribute("oneGroup", group); // przekazany zostanie parametr: ?action=view&solutionId=
				request.getRequestDispatcher(theView).forward(request, response); //preceded by sendredirect w doPost
			}
		}
		
		/**
		 * @return when null, redirects to "groups"
		 */
		private UserGroup getGroup(String idString, HttpServletResponse response) throws ServletException, IOException {

			if (idString == null || idString.length() == 0) {
				response.sendRedirect("groups");
				return null;
			}

			try {
				UserGroup group = groupDao.loadUserGroupById(Integer.parseInt(idString));
				if (group == null) {
					response.sendRedirect("groups");
					return null;
				}
				return group;

			} catch (Exception e) {
				response.sendRedirect("groups");
				return null;
			}

		}

}
