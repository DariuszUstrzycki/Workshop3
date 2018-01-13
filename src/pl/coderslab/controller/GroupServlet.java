package pl.coderslab.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.MySQLUserGroupDao;
import pl.coderslab.dao.UserGroupDao;
import pl.coderslab.model.UserGroup;


@WebServlet("/admin/groups")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserGroupDao groupDao = new MySQLUserGroupDao();
	private final String theView = "/views/groups.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			showForm(request,response);
			break;
		case "delete":
			deleteGroup(request, response); 
			break;
		case "view":
			view(request, response); 
			break;
		case "list":
		default:
			listGroups(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
        if(action == null)
            action = "list";
        switch(action)
        {
            case "create":
                createGroup(request, response);
                break;
            case "list":
            default:
                response.sendRedirect("groups");
                break;
        }
		
	}
	
	private void createGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String groupName = request.getParameter("groupName");

		if (groupName == null || groupName.length() == 0) {
			response.sendRedirect("groups" + "?action=create"); //reloads page; error message is missing
			return;
		} else {
			long id = groupDao.save(new UserGroup(groupName));
			if (id > 0) {
				response.sendRedirect("groups" + "?action=view&show=groupId&groupId=" + id);
			}
		}
	}

	
	
	private void listGroups(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<UserGroup> groupsList = (List<UserGroup>) groupDao.loadAllUserGroups();

		if (groupsList == null) {
			response.sendRedirect("groups");
			return;
		} else {
			Collections.reverse(groupsList); // to improve
			request.setAttribute("groupsList", groupsList);
			
			//show other entities
			String item = request.getParameter("show");
			if (item != null && item.length() > 0) {
				showItem(item, request, response);
			}
			
			request.getRequestDispatcher(theView).forward(request, response);
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
	
	private void showItem(String item, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer id = null;
		try {
		id = Integer.parseInt(request.getParameter("groupId"));
		UserGroup group = groupDao.loadUserGroupById(id);
		if (group == null) {
			response.sendRedirect("groups");
			return;
		} else {
			request.setAttribute("oneGroup", group); // show=exId
		}
		}catch (NumberFormatException e) {
			response.sendRedirect("groups");
			return;
		}
	}

		
		
		
		private void deleteGroup(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			Long groupId = null;
			try {
				groupId = Long.parseLong(request.getParameter("groupId"));
			} catch (NumberFormatException e) {
				response.sendRedirect("groups");
				return;
			}
			
			boolean deleted = groupDao.delete(groupId);
			if (!deleted) { 
				response.sendRedirect("groups");
			}
			
			response.sendRedirect("groups" + "?action=list");
			
		}
		
		private void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.getRequestDispatcher(theView).forward(request, response);
		}

}
