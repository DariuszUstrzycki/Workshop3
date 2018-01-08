package pl.coderslab.controller.solution;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.MySQLSolutionDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;


@WebServlet("/addsolution")
public class AddSolution extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
/*	private LocalDateTime created;
	private LocalDateTime updated;
	private String description;
	private long exerciseId;
	private long userId;*/
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		forwardToFormPage(request, response, "Add a solution to the exercise above");

	}
	
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long exId = Long.valueOf(request.getParameter("exId"));
		String description = request.getParameter("description");
		
		if (nullOrEmpty(description)) {
			forwardToFormPage(request, response, "Ooops, the description field can't be empty!");
			return;
		} else {
			SolutionDao dao = new MySQLSolutionDao();
			User user = (User) request.getSession().getAttribute("loggedUser");
			long id = dao.save(new Solution(description, exId, user.getId()));
			if (id > 0) {
				request.getSession().setAttribute("newSolution", new Solution(id, description, exId, user.getId()));
				forwardToFormPage(request, response, "Solution has been added.");
			}
		}
		
	}
	
	private void forwardToFormPage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		
		request.setAttribute("addSolStatus",  message);

		if (!response.isCommitted()) {
			getServletContext().getRequestDispatcher("/views/addSolution.jsp").forward(request, response);
		}
	}
	
	private boolean nullOrEmpty(String description) {
		return ( description == null || description.equals(""));
	}

	
	

}
