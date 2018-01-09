package pl.coderslab.controller.solution;

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
import pl.coderslab.dao.MySQLSolutionDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

/**
 * Servlet implementation class SolutionServlet
 */
@WebServlet("/solutions")
public class SolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ExerciseDao exDao = new MySQLExerciseDao();
	private final SolutionDao solDao = new MySQLSolutionDao();
	private final String theView = "/views/solutions.jsp";

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			showSolutionForm(request,response);
			break;
		case "view":
			viewOneSolution(request, response); 
			break;
		/*case "download":
			downloadAttachment(request, response);
			break;*/
		case "listForOneExercise":
			someMethod(request, response); 
			break;
		case "list":
		default:
			listSolutions(request, response);
		}
	}
	
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
        if(action == null)
            action = "list";
        switch(action)
        {
            case "create":
                createSolution(request, response);
                break;
            case "list":
            default:
                response.sendRedirect("exercises");
                break;
        }
	}
	
	// TERAZ wyglada na to ze jest zrobione
	private void createSolution(HttpServletRequest request, HttpServletResponse response) throws IOException {
System.out.println("1.Entering createSolution, exId is " + request.getParameter("exId"));
		Long exId = null;
		try {
			exId = Long.parseLong(request.getParameter("exId"));
		} catch (NumberFormatException e) {
			response.sendRedirect("solutions");
			return;
		}

		String description = request.getParameter("description");
		if (nullOrEmpty(description)) {
System.out.println("2.Entering description is null or empty");			
			response.sendRedirect("solutions" + "?action=create&exId=" + exId); // reloads page; error message is missing
			return;
		} else {
			User user = (User) request.getSession().getAttribute("loggedUser");
			long id = solDao.save(new Solution(description, exId, user.getId()));
			if (id > 0) {
	System.out.println("The solution has been saved!");
				response.sendRedirect("solutions" + "?action=view&solId=" + id);
			}
		}
	}
	
	
	
	private void someMethod(HttpServletRequest request, HttpServletResponse response) {

	}
	
	
	
	
		// uruchamia sie po doPost
	private void viewOneSolution(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idString = request.getParameter("solId");
		Solution sol = getSolution(idString, response); 
		System.out.println("Solution found in viewSolution is " + sol);
		if (sol == null) {
			response.sendRedirect("solutions");
			return;
		} else {  
			request.setAttribute("oneSolution", sol); // przekazany zostanie parametr: ?action=view&solutionId=
			request.getRequestDispatcher(theView).forward(request, response); //preceded by sendredirect w doPost
		}
	}
	
	// CAŁE PRZEROBIONE
	private void listSolutions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// getExercises - generateExercises
		List<Solution> allSolutions = (List<Solution>) solDao.loadAllSolutions();

		if (allSolutions == null) {
			response.sendRedirect("solutions");
			return;
		} else {
			Collections.reverse(allSolutions); // to improve
			request.getSession().setAttribute("allSolutions", allSolutions);
			request.getRequestDispatcher(theView).forward(request, response); // mozna forward
		}
		
	}

	
	private void showSolutionForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setAttribute("add", "add");
		
		String exIdString = request.getParameter("exId");
		Exercise ex = getExercise(exIdString, response);
		
		request.setAttribute("oneExercise", ex);
		System.out.println("exId is " + exIdString +  " and the retrieved exercise is " + ex );
		request.getRequestDispatcher(theView).forward(request, response);
	}
	
	private boolean nullOrEmpty( String description) {
		return  description == null || description.equals("");
	}
	
	
	/**
	 * @return when null, redirects to "solutions"
	 */
	private Exercise getExercise(String idString, HttpServletResponse response) throws ServletException, IOException {

		if (idString == null || idString.length() == 0) {
			response.sendRedirect("solutions");
			return null;
		}

		try {
			Exercise ex = exDao.loadExerciseById(Integer.parseInt(idString));
			if (ex == null) {
				response.sendRedirect("solutions");
				return null;
			}
			return ex;

		} catch (Exception e) {
			response.sendRedirect("solutions");
			return null;
		}

	}

	/**
	 * @return when null, redirects to "solutions"
	 */
	private Solution getSolution(String idString, HttpServletResponse response) throws ServletException, IOException {

		if (idString == null || idString.length() == 0) {
			response.sendRedirect("solutions");
			return null;
		}

		try {
			Solution sol = solDao.loadSolutionById(Integer.parseInt(idString));
			if (sol == null) {
				response.sendRedirect("solutions");
				return null;
			}
			return sol;

		} catch (Exception e) {
			response.sendRedirect("solutions");
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
	

	/*private void downloadAttachment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}*/
}
