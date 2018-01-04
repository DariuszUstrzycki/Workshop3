package pl.coderslab.controller.exercise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.MySQLExerciseDao;
import pl.coderslab.model.Exercise;


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
		
		loadExercises(request,response);
		
		if (!response.isCommitted()) {
			response.sendRedirect(request.getContextPath() + "/views/exercises.jsp");
		}
		
	}
	
	private void loadExercises(HttpServletRequest request, HttpServletResponse response) {

		ExerciseDao dao = new MySQLExerciseDao(); // inject
		ArrayList<Exercise> allExercises = (ArrayList<Exercise>) dao.loadAllExercises(); 
		Collections.reverse(allExercises);
		HttpSession session = request.getSession();
			System.out.println("allExercises size is: " + allExercises.size());
		session.setAttribute("allExercises", allExercises);
		session.setAttribute("displayPerPage", displayPerPage);
			System.out.println("displayPerPage is " + displayPerPage);
		
	}

}
