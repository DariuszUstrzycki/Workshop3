package pl.coderslab.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*@WebFilter(description = "blocks access for unlogged users to certain pages", urlPatterns = { "/*" })*/
public class LoginFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("LoginFilter initialized");

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);

		String requestPath = request.getRequestURI();
		System.out.print("-------LoginFilter's action: requestPath " + requestPath);

		if (needsAuthentication(requestPath) && (session == null || session.getAttribute("loggedUser") == null)) { 
			System.out.print(" NEEDS AUTHENTICATION. The LoggedUser is: " + session.getAttribute("loggedUser") + "\n");
			

			if(!response.isCommitted()) {
				response.sendRedirect("home"); // No logged-in user found
			}
				
		} else {
			System.out.print(" DOESN'T NEED AUTHENTICATION\n");
			chain.doFilter(req, res); // Logged-in user found, so just continue request.
		}
	}

	public void destroy() {
	}

	// basic validation of pages that do not require authentication
	private boolean needsAuthentication(String url) {
		String[] validNonAuthenticationUrls = { "/", "login", "login.jsp", "signup", "signup.jsp", "home",
				"index.jsp" };

		for (String validUrl : validNonAuthenticationUrls) {
			if (url.endsWith(validUrl)) {
				return false;
			}
		}
		return true;
	}

}
