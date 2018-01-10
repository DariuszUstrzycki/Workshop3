package pl.coderslab.controller.solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.MySQLExerciseDao;
import pl.coderslab.dao.MySQLSolutionDao;
import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

@WebServlet("/solutions")
public class SolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ExerciseDao exDao = new MySQLExerciseDao();
	private final SolutionDao solDao = new MySQLSolutionDao();
	private final UserDao userDao = new MySQLUserDao();
	private final String theView = "/views/solutions.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			showForm(request, response); // nie interesuje nas: form + 1 exercise
			break;
		case "delete":
			deleteSolution(request, response);
			break;
		case "view":
			viewOneSolution(request, response);
			break;
		case "listInnerJoin":
			listSolutions(request, response);
			break;
		/*
		 * case "download": downloadAttachment(request, response); break;
		 */
		case "list":
		default:
			listSolutions(request, response);
		}

		System.out.println(request.getRequestURL());
		System.out.println(request.getRequestURI());
		System.out.println(request.getQueryString());
	}
	// solutions?action=list&joinOn&joinOn=exercise&exId=7

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			createSolution(request, response);
			break;
		case "list":
		default:
			response.sendRedirect("solutions");
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
			response.sendRedirect("solutions" + "?action=create&exId=" + exId); // reloads page; error message is
																				// missing
			return;
		} else {
			User user = (User) request.getSession().getAttribute("loggedUser");
			long id = solDao.save(new Solution(description, exId, user.getId()));
			if (id > 0) {
				response.sendRedirect("solutions" + "?action=view&solId=" + id);
			}
		}
	}

	// uruchamia sie po doPost
	private void viewOneSolution(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idString = request.getParameter("solId");
		Solution sol = getSolution(idString, response);
		if (sol == null) {
			response.sendRedirect("solutions");
			return;
		} else {
			request.setAttribute("oneSolution", sol); // przekazany zostanie parametr: ?action=view&solutionId=
			request.getRequestDispatcher(theView).forward(request, response); // preceded by sendredirect w doPost
		}
	}

	/**
	 * Generates a list of solutions; optionally adds an entity (a User/a 
	 */
	private void listSolutions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// solutions?action=listInnerJoin&joinOn=exercise&with=exercise&exId=7

		// filter for extras to response
		String entity = request.getParameter("with");
		if (!nullOrEmpty(entity)) {
			addEntityToResponse(entity, request, response);
		}

		// filter for inner join
		List<Solution> solutionList = null;
		String joinOn = request.getParameter("joinOn");
		if (!nullOrEmpty(joinOn)) {
			solutionList = doInnerJoin(solutionList, joinOn, request, response);
		} else {
			solutionList = (List<Solution>) solDao.loadAllSolutions();
		}

		if (solutionList == null) {
			response.sendRedirect("solutions");
			return;
		} else {
			Collections.reverse(solutionList); // to improve
			request.setAttribute("solutionList", solutionList);
			request.getRequestDispatcher(theView).forward(request, response);
		}

	}

	private List<Solution> doInnerJoin(List<Solution> list, String joinOn, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		switch (joinOn) {
		case "exercise": {
			Exercise ex = getExerciseOrRedirectOnError(request.getParameter("exId"), "solutions", response);
			request.setAttribute("oneExercise", ex); // to tez inne

			list = (List<Solution>) solDao.loadSolutionsByExId(ex.getId());
		}
			break;
		case "user": {
			User user = getUserORedirectOnError(request.getParameter("userId"), "solutions", response);
			request.setAttribute("oneUser", user); // to tez inne

			list = (List<Solution>) solDao.loadSolutionsByUserId(user.getId());
		}
			break;
		default:
			response.sendRedirect("solutions");
			return null;
		}

		return new ArrayList<Solution>(list);

	}

	private void showForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// action=create&with=exercise&exId=7

		String entity = request.getParameter("with");
		if (!nullOrEmpty(entity)) {
			addEntityToResponse(entity, request, response);
		}

		request.getRequestDispatcher(theView).forward(request, response);
	}

	private void addEntityToResponse(String entity, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		switch (entity) {
		case "exercise": {
			Exercise ex = getExerciseOrRedirectOnError(request.getParameter("exId"), "solutions", response);
			request.setAttribute("oneExercise", ex);
		}
			break;
		case "user": {
			User sol = getUserORedirectOnError(request.getParameter("userId"), "solutions", response);
			request.setAttribute("oneUser", sol);
		}
			break;
		default:
			response.sendRedirect("solutions");
		}

	}

	private boolean nullOrEmpty(String string) {
		return string == null || string.equals("");
	}

	/**
	 * @return when null, redirects to "solutions"
	 */
	private Exercise getExerciseOrRedirectOnError(String idString, String redirectPage, HttpServletResponse response)
			throws ServletException, IOException {

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
	private User getUserORedirectOnError(String idString, String redirectPage, HttpServletResponse response)
			throws ServletException, IOException {

		if (idString == null || idString.length() == 0) {
			response.sendRedirect(redirectPage);
			return null;
		}

		try {
			User user = userDao.loadUserById(Integer.parseInt(idString));
			if (user == null) {
				response.sendRedirect(redirectPage);
				return null;
			}
			return user;

		} catch (Exception e) {
			response.sendRedirect(redirectPage);
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

	private void deleteSolution(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long solId = null;
		try {
			solId = Long.parseLong(request.getParameter("solId"));
		} catch (NumberFormatException e) {
			response.sendRedirect("solutions");
			return;
		}

		boolean deleted = solDao.delete(solId);
		if (deleted) { // message options: 1) setAttribute (message, "Deleted use userId" 2) dopisac te
						// info do url
			response.sendRedirect("solutions" + "?action=list" + "&solId=" + solId);
		}

	}

	/*
	 * private void downloadAttachment(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException { }
	 */
}
