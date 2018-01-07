package pl.coderslab.controller.exercise;

import java.io.IOException;
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
import pl.coderslab.model.User;

@WebServlet("/addexercise")
public class AddExerciseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ExerciseDao exDao = new MySQLExerciseDao();
	private final String theView = "/views/addExercise.jsp";
	private final String theContext = "/Workshop3"; // init param

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(theContext + theView);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Long userId = getUserId(request);

		if (nullOrEmpty(title, description) || userId == null) {
			request.getSession().setAttribute("message", "Ooops, the title/description fields can't be empty!");
			response.sendRedirect(theContext + theView);
			return;
		} else {
			Exercise ex = new Exercise(title, description, userId);
			persistExercise(ex, request);
			generateExercises(request); // nowe dla view
			response.sendRedirect(theContext + theView);
		}
	}

	private void persistExercise(Exercise ex, HttpServletRequest request) throws ServletException, IOException {
		long id = exDao.save(ex);
		if (id > 0) {
			request.getSession().setAttribute("newExercise",
					new Exercise(id, ex.getTitle(), ex.getDescription(), ex.getUserId())); // niepotrzebne?
			request.getSession().setAttribute("message", "Exercise has been added."); // a tak jest w AdminPanel -
																						// trzeba usunac! bo za dlugo
																						// komunikat jest obecny gdy
																						// jest w sesji
		}
	}
// powtorzone w Exercises Servlet
	private void generateExercises(HttpServletRequest request) {

		List<Exercise> allExercises = (List<Exercise>) exDao.loadAllExercises();
		allExercises = orderByDateDesc(allExercises); // improve one day
		HttpSession session = request.getSession();
		session.setAttribute("allExercises", allExercises);
	}
	// powtorzone w Exercises Servlet
	private List<Exercise> orderByDateDesc(List<Exercise> exercises) {
		Collections.reverse(exercises);
		return exercises;
	}

	private Long getUserId(HttpServletRequest request) {
		Long id = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("loggedUser");
			if (user != null) {
				id = user.getId();
			}
		}
		return id;
	}

	private boolean nullOrEmpty(String title, String description) {
		return (title == null || title.equals("") || description == null || description.equals(""));
	}

}
