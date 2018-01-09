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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			showExerciseForm(request,response);
			break;
		case "delete":
			deleteExercise(request, response); 
			break;
		case "view":
			viewOneExercise(request, response); 
			break;
		/*case "download":
			downloadAttachment(request, response);
			break;*/
		case "list":
		default:
			listExercises(request, response);
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
                createExercise(request, response);
                break;
            case "list":
            default:
                response.sendRedirect("exercises");
                break;
        }
		
	}

	private void createExercise(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Long userId = getUserId(request);

		if (nullOrEmpty(title, description) || userId == null) {
			response.sendRedirect("exercises" + "?action=create"); //reloads page; error message is missing
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
			request.getRequestDispatcher(theView).forward(request, response); //preceded by sendredirect w doPost
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
			request.setAttribute("allExercises", allExercises);
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
	
	private void deleteExercise(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Long exId = null;
		try {
			exId = Long.parseLong(request.getParameter("exId"));
		} catch (NumberFormatException e) {
			response.sendRedirect("exercises");
			return;
		}
		
		boolean deleted = exDao.delete(exId);
		if (deleted) { // message options: 1) setAttribute (message, "Deleted use userId"  2) dopisac te info do url
			response.sendRedirect("exercises" + "?action=list" +"&exId=" + exId);
		}
		
	}
	
	
	private void showExerciseForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(theView).forward(request, response);
	}
	
	private boolean nullOrEmpty(String title, String description) {
		return (title == null || title.equals("") || description == null || description.equals(""));
	}
	

	/*private void downloadAttachment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}*/
}
