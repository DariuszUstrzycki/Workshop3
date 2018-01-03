package pl.coderslab.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.MySQLExerciseDao;
import pl.coderslab.dao.MySQLSolutionDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;


@WebServlet("/exercises")
public class ExercisesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int displayPerPage;

	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Integer displayParam = Integer.parseInt(getServletContext().getInitParameter("numberOfDisplayed"));
			if(displayParam!= null) {
				displayPerPage = displayParam;
			}
		} catch (NumberFormatException e) {
			displayPerPage = 4;
		}
		 
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		loadLatestExercises(request,response);
		
		if (!response.isCommitted()) {
			response.sendRedirect(request.getContextPath() + "/views/exercises.jsp");
		}
		
	}
	
	private void loadLatestExercises(HttpServletRequest request, HttpServletResponse response) {

		ExerciseDao dao = new MySQLExerciseDao(); // inject
		Collection<Exercise> allExercises = dao.loadAllExercises(); 
		HttpSession session = request.getSession();
		session.setAttribute("allExercises", allExercises);
		session.setAttribute("displayPerPage", displayPerPage);
		System.out.println("displayPerPage is " + displayPerPage);
		
	}

}
