package pl.coderslab.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.MySQLExerciseDao;
import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.MySQLUserGroupDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.dao.UserGroupDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.User;
import pl.coderslab.model.UserGroup;


@WebServlet("/admin/panel")
public class AdminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserGroupDao groupDao = new MySQLUserGroupDao();
	UserDao userDao = new MySQLUserDao();   
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		// delete a group - musi byc wczesniej aby view mial aktualne informacje
		String groupId = request.getParameter("deleteGroupId");
		if (groupId != null) {
			boolean deleted = groupDao.delete(Long.parseLong(groupId));
			if (deleted) {
				session.setAttribute("message", "The group has been successfully deleted.");
			} else {
				session.setAttribute("message", "Unexpected problem. The group can't be deleted.");
			}
		}
		
		//display groups and users
		session.setAttribute("allGroups", groupDao.loadAllUserGroups());
		session.setAttribute("allUsers", userDao.loadAllUsers());
		
		//display a form for adding a group
		if(request.getParameter("addGroup") != null) {
			request.setAttribute("showForm", "yes");
			request.getRequestDispatcher("/views/admin_panel.jsp").forward(request, response);
			return;
		}
		
		response.sendRedirect(getServletContext().getContextPath() + "/views/admin_panel.jsp");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// remove a group
		String groupName = request.getParameter("groupName");
		
		if(nullOrEmpty(groupName)){
			forwardToFormPage(request, response, "Ooops, the name field can't be empty!");
			return;
		} else {
			UserGroupDao dao = new MySQLUserGroupDao();
			long id = dao.save(new UserGroup(groupName));
			if (id > 0) {
				request.getSession().setAttribute("newGroup", new UserGroup(groupName));
				request.getSession().setAttribute("message",  "Group has been added.");
				doGet(request, response); // updates the view of groups and by redirection cleans form fields
			}
		}
	}
	
	private boolean nullOrEmpty(String string) {
		return string == null || string.equals("");
	}
	
	private void forwardToFormPage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		
		request.setAttribute("addGroupStatus",  message);

		if (!response.isCommitted()) {     // jak froward to przy refreshu, ponownie przesyla dane z formularza
			response.sendRedirect(getServletContext().getContextPath() + "/views/admin_panel.jsp");
		}
	}
	
	/*private void redirect(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		
		request.getSession().setAttribute("message",  message);

		if (!response.isCommitted()) {     // jak froward to przy refreshu, ponownie przesyla dane z formularza
			response.sendRedirect(getServletContext().getContextPath() + "/views/admin_panel.jsp");
		}
	}*/

}
