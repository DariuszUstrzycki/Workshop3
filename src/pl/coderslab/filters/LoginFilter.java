package pl.coderslab.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

/*@WebFilter(description = "blocks access for unlogged users to certain pages", urlPatterns = { "/*" })*/
public class LoginFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		
		boolean unloggedUser = true;
		Cookie userLoginData = findCookie("remember", request); 
		if(userLoginData != null) {
			logUser(request, session, userLoginData);
			unloggedUser = false;
		} else {
			unloggedUser = (session == null) || (session.getAttribute("loggedUser") == null);
		}
		
		if (needsAuthentication(request.getRequestURI()) && unloggedUser) {
			if (!response.isCommitted()) {
				response.sendRedirect(request.getContextPath() + "/home");
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	private void logUser(HttpServletRequest request, HttpSession session, Cookie userLoginData) {
		int userId = Integer.valueOf(userLoginData.getValue());
		UserDao dao = new MySQLUserDao();
		User user = dao.loadUserById(userId);
		if(session != null) {
			session.setAttribute("loggedUser", user); 
		} else {
			request.getSession().setAttribute("loggedUser", user);
		}
	}

	// basic validation of pages that do not require authentication
	private boolean needsAuthentication(String url) {
		String[] validNonAuthenticationUrls = { "/", "login", "login.jsp", "signup", "signup.jsp", "home",
				"index.jsp", "/cookies", "/Cookie4Show", "/Cookie4Del", "stylesheet.css" };

		for (String validUrl : validNonAuthenticationUrls) {
			if (url.endsWith(validUrl)) {
				return false;
			}
		}
		return true;
	}

	public void destroy() {
	}
	
	
	
	private Cookie findCookie(String cookieName, HttpServletRequest request) {

		if(cookieName != null && !cookieName.equals("")) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (cookieName.equals(c.getName())) {
						return c;
					}
				}
			}
					
		}
		
		return null;		
		
	}

}
