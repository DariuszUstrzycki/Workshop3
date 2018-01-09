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
			showSolutionFormAndExercise(request,response);
			break;
		case "view":
			viewOneSolution(request, response); 
			break;
		/*case "download":
			downloadAttachment(request, response);
			break;*/
		case "listForOneExercise":
			listForOneExercise(request, response); 
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
	
	private void createSolution(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Long exId = null;
		try {
			exId = Long.parseLong(request.getParameter("exId"));
		} catch (NumberFormatException e) {
			response.sendRedirect("solutions");
			return;
		}

		String description = request.getParameter("description");
		if (nullOrEmpty(description)) {
			response.sendRedirect("solutions" + "?action=create&exId=" + exId); // reloads page; error message is missing
			return;
		} else {
			User user = (User) request.getSession().getAttribute("loggedUser");
			long id = solDao.save(new Solution(description, exId, user.getId()));
			if (id > 0) {
				response.sendRedirect("solutions" + "?action=view&solId=" + id);
			}
		}
	}
	
	
	
	private void listForOneExercise(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// action=listForOneExercise&exId=35
			
		Exercise ex = getExercise(request.getParameter("exId"), response);
		
		// getExercises - generateExercises
				List<Solution> allSolsForEx = (List<Solution>) solDao.loadSolutionsByExId(ex.getId());

				if (allSolsForEx == null) {
					response.sendRedirect("solutions");
					return;
				} else {
					System.out.println("allSolsForEx have been created");
					Collections.reverse(allSolsForEx); // to improve
					request.setAttribute("allSolsForEx", allSolsForEx);
					request.setAttribute("oneExercise", ex);
					request.getRequestDispatcher(theView).forward(request, response); 
				}
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
	
	private void listSolutions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// getExercises - generateExercises
		List<Solution> allSolutions = (List<Solution>) solDao.loadAllSolutions();

		if (allSolutions == null) {
			response.sendRedirect("solutions");
			return;
		} else {
			Collections.reverse(allSolutions); // to improve
			request.setAttribute("allSolutions", allSolutions);
			request.getRequestDispatcher(theView).forward(request, response); // mozna forward
		}
		
	}

	
	private void showSolutionFormAndExercise(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Exercise ex = getExercise(request.getParameter("exId"), response);
		request.setAttribute("oneExercise", ex);
		
		request.getRequestDispatcher(theView).forward(request, response);
	}
	
	
	private boolean nullOrEmpty( String string) {
		return  string == null || string.equals("");
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
