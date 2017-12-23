package pl.coderslab.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(description = "blocks access for unlogged users to certain pages", urlPatterns = { "/*" })
public class LoginFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
		  
		        // If you have any <init-param> in web.xml, then you could get them
		        // here by config.getInitParameter("name") and assign it as field.
		fConfig.getServletContext().log("LoginFilter initialized...");
		System.out.println("LoginFilter initialized...");
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String requestPath = request.getRequestURI();
        System.out.println("requestPath intercepted in LoginFilter: " + requestPath);

        if (needsAuthentication(requestPath) &&
              (  session == null || session.getAttribute("loggedUser") == null) ) { // 
        	
        	System.out.println("-12- NEEDS needsAuthentication - returned true!  ");

            response.sendRedirect("home"); // No logged-in user found, so redirect to login page.
        } else {
        	System.out.println("-12- DOESNT NEED needsAuthentication  - Filter does doFilter()");
            chain.doFilter(req, res); // Logged-in user found, so just continue request.
        }
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	//basic validation of pages that do not require authentication
    private boolean needsAuthentication(String url) {
        String[] validNonAuthenticationUrls = {"/", "login",  "login.jsp" , "signup", "signup.jsp", "home", "index.jsp" };

        for(String validUrl : validNonAuthenticationUrls) {
            if (url.endsWith(validUrl)) {
                return false;
            }
        }
        return true;
    }


}
