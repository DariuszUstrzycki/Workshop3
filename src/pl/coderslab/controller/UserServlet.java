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
import pl.coderslab.dao.MySQLUserGroupDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.dao.UserGroupDao;
import pl.coderslab.model.User;
import pl.coderslab.model.UserGroup;


@WebServlet("/admin/users")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserDao userDao = new MySQLUserDao();
	private final UserGroupDao groupDao = new MySQLUserGroupDao();
	private final String theView = "/views/users.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(request.getRequestURL());
		System.out.println(request.getRequestURI());
		System.out.println(request.getQueryString());
		
		String action = request.getParameter("action");
		
		System.out.println("1.Users. action is: " + action);
		
		if (action == null)
			action = "list";
		
		switch (action) {
		/*case "create":
			showSolutionFormAndExercise(request,response);
			break;*/
		case "view":
			view(request, response); 
			break;
		case "delete":
			deleteUser(request, response); 
			break;
		/*case "download":
			downloadAttachment(request, response);
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
            /*case "create":
               createUser(request, response); - not implemented
                break;*/
            case "list":
            default:
                response.sendRedirect("users");
                break;
        }
	}
	
	
	
	private void listUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<User> usersList = null;

		String loadBy = request.getParameter("loadBy");
		if (loadBy != null && loadBy.length() > 0) {
			try {
				switch (loadBy) {
				/*case "exId":
					String exId = request.getParameter("exId");
					if (exId != null & exId.length() > 0) {
						exercisesList = (List<Solution>) exDao.loadExerciseByExId(Integer.parseInt(exId));
					}
					break;*/
				case "groupId":
					String groupId = request.getParameter("groupId");
					if (groupId != null & groupId.length() > 0) {
						usersList = (List<User>) userDao.loadUsersByGroupId((Integer.parseInt(groupId))); 
					}
					break;
				default:
					usersList = (List<User>) userDao.loadAllUsers();
				}
			} catch (NumberFormatException e) {
				response.sendRedirect("users");
				e.printStackTrace();
			}
		} else {
			usersList = (List<User>) userDao.loadAllUsers();
		}

		if (usersList == null) {
			response.sendRedirect("users");
			return;
		} else {
			Collections.reverse(usersList); // to improve
			request.setAttribute("usersList", usersList);
			
			//show other entities
			String item = request.getParameter("show");
			if (item != null && item.length() > 0) {
				showItem(item, request, response);
			}
			
			request.getRequestDispatcher(theView).forward(request, response);
		}
	}
	
	private void showItem(String item, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer id = null;
		try {
			switch (item) {
			case "groupId":
				id = Integer.parseInt(request.getParameter("groupId"));
				UserGroup group = groupDao.loadUserGroupById(id);
				if (group == null) {
					response.sendRedirect("users");
					return;
				} else {
					request.setAttribute("oneGroup", group); // show=groupId
				}
				break;
			case "userId":
				id = Integer.parseInt(request.getParameter("userId"));
				User user = userDao.loadUserById(id);
				if (user == null) {
					response.sendRedirect("users");
					return;
				}
				request.setAttribute("oneUser", user); // show=userId
				break;
			default:
				// do nothing
			}

		} catch (NumberFormatException e) {
			response.sendRedirect("exercises");
			return;
		}
	}
	
	private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String item = request.getParameter("show");
		if (item == null || item.equals("")) {
			return; // no item is supposed to be displayed
		} else {
			showItem(item, request, response);
		}
		
		request.getRequestDispatcher(theView).forward(request, response); 
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
		
		response.sendRedirect("users" + "?action=list");
		
	}
		

}
