package pl.coderslab.controller;

import java.io.IOException;
import java.util.Collection;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		loadLatestSolutions(request, response);
		
		System.out.println("HomeServlet is working...");

		if (!response.isCommitted()) {
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	private void loadLatestSolutions(HttpServletRequest request, HttpServletResponse response) {

		SolutionDao dao = new MySQLSolutionDao(); // inject
		Collection<Solution> allSolutions = dao.loadAllSolutions();
		request.getSession().setAttribute("allSolutions", allSolutions);
		request.setAttribute("solToDisplay", getServletContext().getInitParameter("numberOfSolutions"));
	}

}
