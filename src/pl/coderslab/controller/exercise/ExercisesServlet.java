package pl.coderslab.controller.exercise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	private final ExerciseDao exDao = new MySQLExerciseDao(); 
	private final String theView = "/views/exercises.jsp";
	private final String theContext = "/Workshop3"; // init param

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		generateExercises(request,response);
		response.sendRedirect(theContext + theView);
	}
	
	private void generateExercises(HttpServletRequest request, HttpServletResponse response) {

		List<Exercise> allExercises = (List<Exercise>) exDao.loadAllExercises(); 
		allExercises = orderByDateDesc(allExercises); // improve one day
		HttpSession session = request.getSession();
		session.setAttribute("allExercises", allExercises);
	}
	
	private List<Exercise> orderByDateDesc(List<Exercise> exercises) {
		Collections.reverse(exercises);
		return exercises;
	}

}
