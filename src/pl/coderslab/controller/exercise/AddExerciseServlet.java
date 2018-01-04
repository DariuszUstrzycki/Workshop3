package pl.coderslab.controller.exercise;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.MySQLExerciseDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.User;

@WebServlet("/addexercise")
public class AddExerciseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(request.getContextPath() + "/views/addExercise.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		
		System.out.println("Entering post with " + title + ", " + description);
		
		if (nullOrEmpty(title, description)) {
			forwardToFormPage(request, response, "Ooops, the title/description fields can't be empty!");
			return;
		} else {
			ExerciseDao dao = new MySQLExerciseDao();
			System.out.println("About to add exercise");
			long id = dao.save(new Exercise(title, description));
			if (id > 0) {
				System.out.println("id of exercise is " + id);
				request.getSession().setAttribute("newExercise", new Exercise(id, title, description));
				forwardToFormPage(request, response, "Exercise has been added.");
			}
		}
		
	}
	
	private boolean nullOrEmpty(String title, String description) {
		return (title == null || title.equals("") || description == null || description.equals(""));
	}
	
	private void forwardToFormPage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		
		request.setAttribute("addExStatus",  message);

		if (!response.isCommitted()) {
			getServletContext().getRequestDispatcher("/views/addExercise.jsp").forward(request, response);
		}
	}

}
