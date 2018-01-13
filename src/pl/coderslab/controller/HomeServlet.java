package pl.coderslab.controller;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.MySQLSolutionDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.model.Solution;

@WebServlet({ "/home" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int numberOfDisplayed; // usunac to dziadostwo, bo nigdzie indziej go nie ma
	private SolutionDao dao = new MySQLSolutionDao(); // inject

	@Override
	public void init() throws ServletException {
		Integer displayParam = -1;
		try {
			displayParam = Integer.parseInt(getServletContext().getInitParameter("numberOfDisplayed"));
			if (displayParam != null) {
				numberOfDisplayed = displayParam;
			}
		} catch (NumberFormatException e) {
			numberOfDisplayed = 5;
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		loadSolutions(request, response);
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void loadSolutions(HttpServletRequest request, HttpServletResponse response) {

		List<Solution> solutionsList = (List<Solution>) dao.loadAllSolutions(numberOfDisplayed);

		if (solutionsList != null) {
			Collections.reverse(solutionsList); // remove later
			request.setAttribute("solutionsList", solutionsList);
		}
	}

}
