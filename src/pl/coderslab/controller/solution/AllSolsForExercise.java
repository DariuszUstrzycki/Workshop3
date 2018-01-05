package pl.coderslab.controller.solution;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.MySQLSolutionDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;


@WebServlet("/allsolsforex")
public class AllSolsForExercise extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		long exId = Long.parseLong(request.getParameter("exId"));
		SolutionDao dao = new MySQLSolutionDao();
		ArrayList<Solution> solutionsForEx =  (ArrayList<Solution>) dao.loadSolutionsByExId(exId);
		
		request.getSession().setAttribute("solutionsForEx", solutionsForEx);
		
		request.getRequestDispatcher("/views/allSolsForEx.jsp").forward(request, response);
	}

	

}
