package pl.coderslab.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class First extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public First() {
        super();
        System.out.println( "-----" +   "FIRST constructor is called----------" );
    }

	
    
	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println( "-----"  + getServletName() + " init() is called----------" );
	}



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
