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

@WebServlet("/exercises")
public class ExercisesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ExerciseDao exDao = new MySQLExerciseDao();
	private final String theView = "/views/exercises.jsp";
	private final String theContext = "/Workshop3"; // init param

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			this.showExerciseForm(request,response);
			break;
		case "view":
			this.viewOneExercise(request, response); 
			break;
		/*case "download":
			this.downloadAttachment(request, response);
			break;*/
		case "list":
		default:
			this.listExercises(request, response);
			break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// add exercise
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Long userId = getUserId(request);

		if (nullOrEmpty(title, description) || userId == null) {
			request.getSession().setAttribute("message", "Ooops, the title/description fields can't be empty!");
			response.sendRedirect(theContext + theView);
			return;
		} else {
			long id = exDao.save(new Exercise(title, description, userId));
			if (id > 0) {
				response.sendRedirect("exercises" + "?action=view&exerciseId=" + id);
			}
		}
	}

		// uruchamia sie po doPost
	private void viewOneExercise(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idString = request.getParameter("exerciseId");
		Exercise ex = this.getExercise(idString, response); // jesli nie bedzie bledow zwraca ex o tym id lub null
		
		if (ex == null) {
			response.sendRedirect("exercises");
			return;
		} else {  
			request.setAttribute("oneExercise", ex); // przekazany zostanie parametr: ?action=view&exerciseId=
			request.getRequestDispatcher(theView).forward(request, response); // wczesniej byl sendredirect w doPost
		}
		
	}
	
	

	private void listExercises(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// getExercises - generateExercises
		List<Exercise> allExercises = (List<Exercise>) exDao.loadAllExercises();
		
		if (allExercises == null) {
			response.sendRedirect("exercises");
			return;
		} else { 
			Collections.reverse(allExercises); // to improve
			request.getSession().setAttribute("allExercises", allExercises);
			request.getRequestDispatcher(theView).forward(request, response); // mozna forward 
		}
		
	}

	
	 /**
	 * @return when null, redirects to "exercises"
	 */
	private Exercise getExercise(String idString, HttpServletResponse response) throws ServletException, IOException {
		 
		if (idString == null || idString.length() == 0) {
			response.sendRedirect("exercises");
			return null;
		}

		try {
			Exercise ex = this.exDao.loadExerciseById(Integer.parseInt(idString));
			if (ex == null) {
				response.sendRedirect("exercises");
				return null;
			}
			return ex;

		} catch (Exception e) {
			response.sendRedirect("exercises");
			return null;
		}
	 }
	 
	 //////////////////////////////////////////////////////////////
	 
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
	

	
	/*private void downloadAttachment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}*/
	
	private boolean nullOrEmpty(String title, String description) {
		return (title == null || title.equals("") || description == null || description.equals(""));
	}
	

	
	private void showExerciseForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("add", "add");
		request.getRequestDispatcher(theView).forward(request, response);
	}
}
