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
import pl.coderslab.model.Solution;
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
		/*case "download":
		downloadAttachment(request, response);
		break;*/
		case "create":
			showForm(request,response);
			break;
		case "delete":
			deleteExercise(request, response); 
			break;
		case "view":
			viewOneExercise(request, response);  //. ma byc  list(...)
			break;
		case "list":
		default:
			OLDlistExercises(request, response);
		}
		
		System.out.println(request.getRequestURL());
		System.out.println(request.getRequestURI());
		System.out.println(request.getQueryString());
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
		if(title == null || title.length() == 0 || description == null || description.length() == 0) {
			response.sendRedirect("exercises" + "?action=create"); // reloads page; 
			return;
		} 
			
		User user = (User) request.getSession().getAttribute("loggedUser");
		if (user == null) {
			response.sendRedirect("exercises");
			return;
		}
		
		Long userId = user.getId();
		long id = exDao.save(new Exercise(title, description, userId));
		if (id > 0) {
			response.sendRedirect("exercises" + "?action=view&show=exId&exId=" + id);
		} else {
			response.sendRedirect("exercises");
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
	
	private void OLDlistExercises(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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

	

	private void listExercises(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Exercise> exercisesList = null;

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
				case "userId":
					String userId = request.getParameter("userId");
					if (userId != null & userId.length() > 0) {
						exercisesList = (List<Exercise>) exDao.loadExercisesByUserId((Integer.parseInt(userId))); // not implemented!!!
					}
					break;
				default:
					exercisesList = (List<Exercise>) exDao.loadAllExercises();
				}
			} catch (NumberFormatException e) {
				response.sendRedirect("solutions");
				e.printStackTrace();
			}
		} else {
			exercisesList = (List<Exercise>) exDao.loadAllExercises();
		}

		if (exercisesList == null) {
			response.sendRedirect("exercises");
			return;
		} else {
			Collections.reverse(exercisesList); // to improve
			request.setAttribute("exerciseList", exercisesList);
			
			//show other entities
			String item = request.getParameter("show");
			if (item != null && item.length() > 0) {
				showItem(item, request, response);
			}
			
			request.getRequestDispatcher(theView).forward(request, response);
		}
	}
	
	
	
	 /**
	 * @return when null, redirects to "exercises"
	 */
	private Exercise getExercise(String idString, HttpServletResponse response) throws ServletException, IOException {
		 
		
		
		
		
		/////////////////////////// stare ponizej /////////////////
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
		if (!deleted) { 
			response.sendRedirect("exercises");
			return;
		}
		
		//return the same view from which the delete request came
		
		String previousPageData = request.getParameter("returnTo");
		String returnView = "";
		if (previousPageData != null && previousPageData.length() > 0) {

			String listExercisesForTheUserView = "&loadBy=userId&show=userId&userId=";

			if (previousPageData.contains("loadBy_userId_show_" + "userId")) {
				String userId = previousPageData.replaceAll("[^0-9]", "");
				returnView += listExercisesForTheUserView + userId;
			} 

		}
		
		response.sendRedirect("exercises" + "?action=list" + returnView);
		
	}
	
	
	private void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher(theView).forward(request, response);
	}
	

	/*private void downloadAttachment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}*/
}
