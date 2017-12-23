package pl.coderslab.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TestSessionServlet
 */
@WebServlet("/session")
public class TestSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.append("gżegłółką");
		
		HttpSession session = request.getSession();
		long creationTime = session.getCreationTime();
		long lastAccessed = session.getLastAccessedTime();
		Date creation = new Date(creationTime);
		Date lastAccess = new Date(lastAccessed);
		String id = session.getId();
		out.append("\n Session is new: " + session.isNew());
		out.append("\n").append("Session id:" + id + "\n created: " + creation + "\n last accessed: " + lastAccess);
		int minutes = session.getMaxInactiveInterval() / 30;
		out.append("\n maximum inactive interval in minutes: " + minutes);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
