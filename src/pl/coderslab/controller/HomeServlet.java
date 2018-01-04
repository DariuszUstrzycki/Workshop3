package pl.coderslab.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
	private int displayPerPage;
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Integer displayParam = Integer.parseInt(getServletContext().getInitParameter("numberOfDisplayed"));
			if(displayParam!= null) {
				displayPerPage = displayParam;
			}
		} catch (NumberFormatException e) {
			displayPerPage = 4;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		loadSolutions(request, response);
		
		System.out.println("HomeServlet is working...");

		if (!response.isCommitted()) {
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	private void loadSolutions(HttpServletRequest request, HttpServletResponse response) {

		SolutionDao dao = new MySQLSolutionDao(); // inject
		ArrayList<Solution> allSolutions = (ArrayList<Solution>) dao.loadAllSolutions(); 
		Collections.reverse(allSolutions);
		HttpSession session = request.getSession();
		session.setAttribute("allSolutions", allSolutions);
		session.setAttribute("displayPerPage", displayPerPage); 
	}

}
