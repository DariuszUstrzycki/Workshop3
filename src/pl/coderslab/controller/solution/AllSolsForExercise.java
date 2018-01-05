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
		System.out.println("Solutions for id " + exId);
		
		String info = "Recorded in /allsolsforex. time is " + LocalTime.now() + "<br>";
		
		if(solutionsForEx != null)
			info += "solutionsForEx.size " + solutionsForEx.size();
		else
			info += "solutionsForEx is null, ";
		
		info += ", exId " + exId + ", ";
		
		
		info += "///////////";
		
		
		// session.setAttribute("allExercises", allExercises); w /exercises
		ArrayList<Exercise> allExercises =  (ArrayList<Exercise>) request.getSession().getAttribute("allExercises");
		if(allExercises != null)
			info += " allExercises.size " + allExercises.size();
		else
			info += " allExercises is null, ";
		
		info += "///////////";
		
		
		ArrayList<Exercise> allSolutions =  (ArrayList<Exercise>) request.getSession().getAttribute("allSolutions");
		if(allSolutions != null)
			info += " allSolutions.size " + allSolutions.size();
		else
			info += " allSolutions is null, ";
		
		
		info += "///////////";
		
		
		String exIndex = request.getParameter("exIndex");
		if(exIndex != null)
			info += " exIndex is " + exIndex + ", ";
		else
			info += " exIndex is null, ";
		
		
		info += "<br> aby wyswietlic selected exercise potrzeba ${allExercises[param.exIndex].id}"; // ${allSolutions}
		info += "<br> aby wyswietlic wszystkie solutions potrzeba ${allSolutions}";
		info += "<br> To co nie ginie, czyli solutions for Given ex wymaga ${solutionsForEx}, ktore jest tworzone w tym servlecie";
				
		System.out.println(info);
		
		request.setAttribute("info", info);
		
		request.getSession().setAttribute("solutionsForEx", solutionsForEx);
		
		request.getRequestDispatcher("/views/allSolsForEx.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
