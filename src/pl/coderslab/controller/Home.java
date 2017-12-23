package pl.coderslab.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.UserDao;
//import pl.coderslab.dao.SolutionDtoDao;
//import pl.coderslab.dao.UserDao;
import pl.coderslab.db.DbUtil;
import pl.coderslab.model.User;

/**
 * Servlet implementation class Home
 */







@WebServlet({"/home"})
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();
			
			String hashedPs = BCrypt.hashpw("123", BCrypt.gensalt(15));//this is when you insert
			boolean passwordMatch_1 = BCrypt.checkpw( "123" , hashedPs); 
			System.out.println("Test 1 - Do they match: " + passwordMatch_1);
			
		/*	UserDao dao = new MySQLUserDao();
			User darco = dao.loadUserById(45);
			String darcoPassword = darco.getPassword();
			System.out.println("Darco password is " + darcoPassword);*/
			
			
			
			//BCrypt.hashpw(password, BCrypt.gensalt());
		/*	boolean match = BCrypt.checkpw("123", darcoPassword);
			System.out.println("123 matches Darco password: " + match);
*/
			
			// int limit = Integer.parseInt(getServletContext().getInitParameter("number-solutions"));
			
			out.append("Home file before making the connection: ");
			Connection conn = DbUtil.getConn();
			out.append("Home file after making the connection: ");

			//SolutionDto[] solutions = SolutionDtoDao.loadSolutionsWithUsers(conn, limit);
			conn.close();
			//request.setAttribute("solutions", solutions);
		//	getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
