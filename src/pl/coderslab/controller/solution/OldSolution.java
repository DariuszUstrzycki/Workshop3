package pl.coderslab.controller.solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

/**
 * Servlet implementation class OldSolution
 */
@WebServlet("/OldSolution")
public class OldSolution extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OldSolution() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

	/*
	*//**
	 * Generates a list of solutions; optionally adds an entity (a User/a
	 *//*
	private void listSolutionsOld(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// solutions?action=list&joinOn=exercise&with=exercise&exId=7

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

	}*/
/*
	private boolean nullOrEmpty(String string) {
		return string == null || string.equals("");
	}

	*//**
	 * @return when null, redirects to "solutions"
	 *//*
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

	*//**
	 * @return when null, redirects to "solutions"
	 *//*
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

	*//**
	 * @return when null, redirects to "solutions"
	 *//*
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

	}*/


}
