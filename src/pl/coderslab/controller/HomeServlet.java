package pl.coderslab.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.dao.MySQLSolutionDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.SolutionDtoDao;
import pl.coderslab.model.SolutionDto;

@WebServlet({ "/home" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int numberOfDisplayed; // usunac to dziadostwo, bo nigdzie indziej go nie ma
	private SolutionDao dao = new MySQLSolutionDao(); // inject
	private final SolutionDtoDao solDtoDao = new SolutionDtoDao();

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
		///////////////////////////
		
		
		/*MySQLAttachmentDao dao = new MySQLAttachmentDao();
		List<Attachment> list =  (List<Attachment>) dao.loadAttachmentByAttachedToId(19, "solution");
		if(list != null)
			System.out.println(">>>>>>att list size is " + list.size());
		else 
			System.out.println(">>>>>>>att list size is " + list.size());*/
		
		
		List<SolutionDto> solutionDtoList =  solDtoDao.loadSolutionDto();

		if (solutionDtoList == null) {
			//response.sendRedirect("solutions"); ???
			return;
		} else {
			request.setAttribute("solutionDtoList", solutionDtoList);
			
		}

		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	

}
