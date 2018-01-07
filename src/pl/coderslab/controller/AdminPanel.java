package pl.coderslab.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.MySQLUserGroupDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.dao.UserGroupDao;
import pl.coderslab.model.UserGroup;

@WebServlet("/admin/panel")
public class AdminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserGroupDao groupDao = new MySQLUserGroupDao();
	private final UserDao userDao = new MySQLUserDao();
	private final String theView = "/views/admin_panel.jsp";
	private final String theContext = "/Workshop3"; // init param

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		// comes before groups and users are generated for the view
		String groupId = request.getParameter("deleteGroupId");
		if (groupId != null) {
			deleteGroup(groupId, session);
		}

		String userId = request.getParameter("deleteUserId");
		if (userId != null) {
			deleteUser(userId, session);
		}

		generateGroupsAndUsers(session);
		response.sendRedirect(theContext + theView);

	}

	private void generateGroupsAndUsers(HttpSession session) {
		session.setAttribute("allGroups", groupDao.loadAllUserGroups());
		session.setAttribute("allUsers", userDao.loadAllUsers());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String groupName = request.getParameter("groupName");
		if (nullOrEmpty(groupName)) {
			request.getSession().setAttribute("message", "Ooops, the name field can't be empty!");
			response.sendRedirect(theContext + theView);
		} else {
			persistGroup(groupName, request, response);
			generateGroupsAndUsers(session);
			response.sendRedirect(theContext + theView);
			// doGet(request, response); // updates the view of groups and redirection
			// clears form fields
		}

	}

	private boolean nullOrEmpty(String string) {
		return string == null || string.equals("");
	}

	private void deleteGroup(String groupId, HttpSession session) {
		boolean deleted = groupDao.delete(Long.parseLong(groupId));
		if (deleted) {
			session.setAttribute("message", "The group has been successfully deleted."); // to raczej nie na sesji; moze url encoded?
		} else {
			session.setAttribute("message", "Unexpected problem. The group can't be deleted.");
		}
	}

	private void deleteUser(String userId, HttpSession session) {
		boolean deleted = userDao.delete(Long.parseLong(userId));
		if (deleted) {
			session.setAttribute("message", "The user has been successfully deleted.");
		} else {
			session.setAttribute("message", "Unexpected problem. The user can't be deleted.");
		}
	}

	private void persistGroup(String groupName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long id = groupDao.save(new UserGroup(groupName));
		if (id > 0) {
			request.getSession().setAttribute("message", "Group has been added.");
		}
	}

}
