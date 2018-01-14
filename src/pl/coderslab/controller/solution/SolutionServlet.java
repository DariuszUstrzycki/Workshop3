package pl.coderslab.controller.solution;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import pl.coderslab.dao.AttachmentDao;
import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.MySQLAttachmentDao;
import pl.coderslab.dao.MySQLExerciseDao;
import pl.coderslab.dao.MySQLSolutionDao;
import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Attachment;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

@WebServlet("/solutions")

@MultipartConfig(
        fileSizeThreshold = 5_242_880, //5MB //5MB tells the web container how big the file has to be
        						//before it is written to the temporary directory. uploaded files smaller than 5 megabytes are kept in memory until the request completes
        		//and then they become eligible for garbage. After a file exceeds 5 megabytes, the container instead stores
        		//it in location (or default) until the request completes, after which it deletes the file from disk.
        maxFileSize = 20_971_520L, //20MB
        maxRequestSize = 41_943_040L //40MB
)
public class SolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final AttachmentDao attachDao = new MySQLAttachmentDao();
	private final ExerciseDao exDao = new MySQLExerciseDao();
	private final SolutionDao solDao = new MySQLSolutionDao();
	private final UserDao userDao = new MySQLUserDao();
	private final String theView = "/views/solutions.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		/*
		 * case "download": downloadAttachment(request, response); break;
		 */
		case "create": //solutions?action=create&exId=15 lub solutions?action=create&show=exId&exId=15
			showForm(request, response); 
			break;
		case "delete": //solutions?action=delete&solId=15 
			deleteSolution(request, response);
			break;
		case "view": // solutions?action=view&solId=15
			view(request, response); 
			break;
		case "list":
		default:
			listSolutions(request, response); // solutions?action=list&loadBy=exId&show=exId&exId=15
		}

		System.out.println(request.getRequestURL());
		System.out.println(request.getRequestURI());
		System.out.println(request.getQueryString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create": // solutions?action=view&solId=17
			createSolution(request, response);
			break;
		case "list":
		default:
			response.sendRedirect("solutions");
			break;
		}
	}
	
	private void downloadAttachment(String solutionId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String solIdString = request.getParameter(solutionId);
		if(solIdString == null) {
			response.sendRedirect("solutions" + "?action=view" + "&solId=" + solutionId); 
		}
		
		Long solId = Long.parseLong(solIdString);	

		Attachment attachment =  (Attachment) attachDao.loadAttachmentByAttachedToId(solId, "solution"); 
		if (attachment == null) {
			response.sendRedirect("solutions" + "?action=view" + "&solId=" + solutionId);
			return;
		}

		response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
		response.setContentType("application/octet-stream");

		ServletOutputStream stream = response.getOutputStream();
		stream.write(attachment.getContents());
	}


	private void showItem(String item, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer id = null;
		try {
			switch (item) {
			case "exId":
				id = Integer.parseInt(request.getParameter("exId"));
				Exercise ex = exDao.loadExerciseById(id);
				if (ex == null) {
					response.sendRedirect("solutions");
					return;
				} else {
					request.setAttribute("oneExercise", ex); // show=exId
				}
				break;
			case "userId":
				id = Integer.parseInt(request.getParameter("userId"));
				User user = userDao.loadUserById(id);
				if (user == null) {
					response.sendRedirect("solutions");
					return;
				}
				request.setAttribute("oneUser", user); // show=userId
				break;
			case "solId":
				id = Integer.parseInt(request.getParameter("solId"));
				Solution sol = solDao.loadSolutionById(id);
				if (sol == null) {
					response.sendRedirect("solutions");
					return;
				}
				request.setAttribute("oneSolution", sol);// show=solId
			default:
				// do nothing
			}

		} catch (NumberFormatException e) {
			response.sendRedirect("solutions");
			return;
		}
	}

	
	private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String item = request.getParameter("show");
		if (item == null || item.equals("")) {
			return; // no item is supposed to be displayed
		} else {
			showItem(item, request, response);
		}
		
		request.getRequestDispatcher(theView).forward(request, response); 
	}

	private void listSolutions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Solution> solutionsList = null;

		String loadBy = request.getParameter("loadBy");
		if (loadBy != null && loadBy.length() > 0) {
			try {
				switch (loadBy) {
				case "exId":
					String exId = request.getParameter("exId");
					if (exId != null & exId.length() > 0) {
						solutionsList = (List<Solution>) solDao.loadSolutionsByExId(Integer.parseInt(exId));
					}
					break;
				case "userId":
					String userId = request.getParameter("userId");
					if (userId != null & userId.length() > 0) {
						solutionsList = (List<Solution>) solDao.loadSolutionsByUserId(Integer.parseInt(userId));
					}
					break;
				/*case "solId":
					String solId = request.getParameter("solId");
					if (solId != null & solId.length() > 0) {
						solutionsList = (List<Solution>) solDao.loadSolutionsByExId(Integer.parseInt(solId));
					}
					break;*/
				default:
					solutionsList = (List<Solution>) solDao.loadAllSolutions();
				}
			} catch (NumberFormatException e) {
				response.sendRedirect("solutions");
				e.printStackTrace();
			}
		} else {
			solutionsList = (List<Solution>) solDao.loadAllSolutions();
		}

		if (solutionsList == null) {
			response.sendRedirect("solutions");
			return;
		} else {
			request.setAttribute("solutionsList", solutionsList);
			
			//show other entities
			String item = request.getParameter("show");
			if (item != null && item.length() > 0) {
				showItem(item, request, response);
			}
			
			request.getRequestDispatcher(theView).forward(request, response);
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
		if(!deleted) {
			response.sendRedirect("solutions");
			return;
		}
		
		//return the same view from which the delete request came
					
		String previousPageData = request.getParameter("returnTo");
		String returnView = "";
		if (previousPageData != null && previousPageData.length() > 0) {

			String listSolutionsForTheUserView = "&loadBy=userId&show=userId&userId=";
			String listSolutionsFortheExerciseView = "&loadBy=exId&show=exId&exId=";

			if (previousPageData.contains("loadBy_userId_show_" + "userId")) {
				String userId = previousPageData.replaceAll("[^0-9]", "");
				returnView += listSolutionsForTheUserView + userId;
			} else if (previousPageData.contains("loadBy_exId_show_"+ "exId")) {
				String exId = previousPageData.replaceAll("[^0-9]", "");
				returnView += listSolutionsFortheExerciseView + exId;
			}

		}
		
		response.sendRedirect("solutions" + "?action=list" + returnView);

	}
	
	private void showForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// TERAZ LINK Z WIDOKU BEDZIE DECYDOWAL CZY WYSWIETLIC EXERCISE CZY NIE. POSREDNIO JUZ TO ROBIL, BO BYLO TAM
		// ZAPISANE "oneExercise". Ale teraz w nowej wersji, tylko z linku sie dowiesz ze to ma byc zapisane
		//show=exId&exId=15
		
		Integer id = null;
		try {
			id = Integer.parseInt(request.getParameter("exId"));
			Exercise ex = exDao.loadExerciseById(id);
			if (ex == null) {
				response.sendRedirect("solutions");
				return;
			} else {
				request.setAttribute("oneExercise", ex); // show=exId
			}
		} catch (NumberFormatException e) {
			response.sendRedirect("solutions");
			return;
		}		

		request.getRequestDispatcher(theView).forward(request, response);
	}
	

	private void createSolution(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long exId = null;
		try {
			exId = Long.parseLong(request.getParameter("exId"));
		} catch (NumberFormatException e) {
			response.sendRedirect("solutions");
			return;
		}

		String description = request.getParameter("description");
		if (description == null || description.length() == 0) {
			response.sendRedirect("solutions" + "?action=create&exId=" + exId); // reloads page;no error message
			return;
		} else {
			User user = (User) request.getSession().getAttribute("loggedUser");
			
			long id = solDao.save(new Solution(description, exId, user.getId())); 
			if (id > 0) {

				///////////////////mozemy dodac attachement/////////////////////
				Part filePart = request.getPart("file1"); 
				if (filePart != null && filePart.getSize() > 0) {
					Attachment attachment = this.processAttachment(filePart);
					if (attachment != null) {
						attachment.setAttachedToId(id);
						long attId = attachDao.save(attachment, "solution"); 
					}
				}
				//brakuje odwolania do url linka o dodanym attachement
				response.sendRedirect("solutions" + "?action=view&show=solId&solId=" + id); 
			} else {
				response.sendRedirect("solutions");
			}
		}
		
		
		
	}

	 private Attachment processAttachment(Part filePart)
	            throws IOException
	    {
	        InputStream inputStream = filePart.getInputStream();
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	        int read;
	        final byte[] bytes = new byte[1024];

	        while((read = inputStream.read(bytes)) != -1)
	        {
	            outputStream.write(bytes, 0, read);
	        }

	        Attachment attachment = new Attachment();
	        attachment.setName(filePart.getSubmittedFileName());
	        attachment.setMimeType(filePart.getContentType()); 
	        attachment.setContents(outputStream.toByteArray());

	        return attachment;
	    }
	
	

	/*
	 * private void downloadAttachment(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException { }
	 */
}
