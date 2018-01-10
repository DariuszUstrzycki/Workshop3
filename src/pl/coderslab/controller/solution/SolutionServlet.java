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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			showAddForm(request,response); // nie interesuje nas: form + 1 exercise
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
		/*case "download":
			downloadAttachment(request, response);
			break;*/
		case "list":
		default:
			listSolutions(request, response);
		}
		
		System.out.println(request.getRequestURL());
		System.out.println(request.getRequestURI());
		System.out.println(request.getQueryString());
	}
	// solutions?action=list&joinOn&joinOn=exercise&exId=7
	
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
	
	// uruchamia sie po doPost
	private void viewOneSolution(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idString = request.getParameter("solId");
		Solution sol = getSolution(idString, response); 
		if (sol == null) {
			response.sendRedirect("solutions");
			return;
		} else {  
			request.setAttribute("oneSolution", sol); // przekazany zostanie parametr: ?action=view&solutionId=
			request.getRequestDispatcher(theView).forward(request, response); //preceded by sendredirect w doPost
		}
	}
	
	
	
	private void zzz (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//action=listForOneExercise&exId=38
		// nowy  action=list&with=exercise&joinOn=exercise&exId=35
		
		List<Solution> solutionList = null;
		
		String on = request.getParameter("on");
		if (!nullOrEmpty(on)) {
			switch(on) {
			case "exercise": {
				Exercise ex = getExercise(request.getParameter("exId"), response);
				solutionList = (List<Solution>) solDao.loadSolutionsByExId(ex.getId());
				request.setAttribute("oneExercise", ex);  // to tez inne
			}
				break;
			case "user": {
				User user = getUser(request.getParameter("userId"), response);
		        solutionList = (List<Solution>) solDao.loadSolutionsByUserId(user.getId());
		        request.setAttribute("oneUser", user);  // to tez inne
			}
				break;
				default:
					//
			}
		}
				// if dotyczy wszystkich
				if (solutionList == null) {
					response.sendRedirect("solutions");
					return;
				} else {
					Collections.reverse(solutionList); 
					request.setAttribute("allSolsForEx", solutionList); // to innedla roznych
					request.getRequestDispatcher(theView).forward(request, response); 
				}
	}
	
	
	
	private void listSolutions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// action=list&joinOn=exercise&with=exercise&exId=35
		// solutions?action=listInnerJoin&on=exercise&exId=3
		
		
	
		
		String with = request.getParameter("with");
		boolean addEntity = !nullOrEmpty(with);
		
		if(addEntity) {
			switch(with) {  // true
			case "exercise": {
				Exercise ex = getExercise(request.getParameter("exId"), response);
				request.setAttribute("oneExercise", ex);
				System.out.println("1....ex: " + ex);
			}
				break;
			case "user": {
				User sol = getUser(request.getParameter("userId"), response);
				request.setAttribute("oneUser", sol);
				System.out.println("2...........");
			}
				break;
				default:
					response.sendRedirect("solutions");
			}		
			
		}
		
		String joinOn = request.getParameter("joinOn");
		boolean doInnerJoin = !nullOrEmpty(joinOn);
		
		List<Solution> solutionList = null;
		if(!doInnerJoin) {
			solutionList = (List<Solution>) solDao.loadAllSolutions();
			System.out.println("3...........");
		}
		
		if(doInnerJoin) {
			switch(joinOn) {
			case "exercise": {
				Exercise ex = getExercise(request.getParameter("exId"), response);
				request.setAttribute("oneExercise", ex);  // to tez inne
				
				solutionList = (List<Solution>) solDao.loadSolutionsByExId(ex.getId());
				System.out.println("4........ex: " + ex);
			}
				break;
			case "user": {
				User user = getUser(request.getParameter("userId"), response);
		        request.setAttribute("oneUser", user);  // to tez inne
		        
		        solutionList = (List<Solution>) solDao.loadSolutionsByUserId(user.getId());
		        System.out.println("5...........");
			}
				break;
				default:
					response.sendRedirect("solutions");
			}
			
			
			
			request.setAttribute("innerJoinOn_" + joinOn, solutionList); // to innedla roznych
		}

		if (solutionList == null) {
			response.sendRedirect("solutions");
			return;
		} else {
			
			System.out.println("5..solutionList size; " + solutionList.size());
			Collections.reverse(solutionList); // to improve   
			request.setAttribute("solutionList", solutionList);
			request.getRequestDispatcher(theView).forward(request, response); 
		}
		
	}
	
	private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// action=list&with=exercise&exId=35
		// Sdds attribute to the add solution form
				String with = request.getParameter("with");
				if (!nullOrEmpty(with)) {
					switch(with) {
					case "exercise": {
						Exercise ex = getExercise(request.getParameter("exId"), response);
						request.setAttribute("oneExercise", ex);
					}
						break;
					case "user": {
						User sol = getUser(request.getParameter("userId"), response);
						request.setAttribute("oneUser", sol);
					}
						break;
						default:
						// do nothing
					}
				}
				
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
	private User getUser(String idString, HttpServletResponse response) throws ServletException, IOException {
		
		if (idString == null || idString.length() == 0) {
			response.sendRedirect("solutions");
			return null;
		}

		try {
			User user = userDao.loadUserById(Integer.parseInt(idString));
			if (user == null) {
				response.sendRedirect("solutions");
				return null;
			}
			return user;

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
		if (deleted) { // message options: 1) setAttribute (message, "Deleted use userId"  2) dopisac te info do url
			response.sendRedirect("solutions" + "?action=list" +"&solId=" + solId);
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
