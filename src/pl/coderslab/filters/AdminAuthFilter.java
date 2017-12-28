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

import pl.coderslab.model.User;

/*@WebFilter("/admin/*")*/
public class AdminAuthFilter implements Filter {

	private String adminLogin = "admin";

	public void init(FilterConfig fConfig) throws ServletException {

		String param = fConfig.getServletContext().getInitParameter("adminLogin");
		if (param != null)
			adminLogin = param;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);

		String userName = null;

		if (session != null) {
			User user = (User) session.getAttribute("loggedUser");
			if (user != null) {
				userName = user.getUsername();
			}
		}

		if (userName == null || !userName.equals(adminLogin)) {
			response.sendRedirect(request.getContextPath() + "/home"); // request.getServletContext().getContextPath()
			return;
		} else {
			chain.doFilter(req, res);
		}

	}

	public void destroy() {
	}

}
