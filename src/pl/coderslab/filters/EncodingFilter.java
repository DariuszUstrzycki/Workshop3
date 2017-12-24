package pl.coderslab.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter(urlPatterns = { "/*" })
public class EncodingFilter implements Filter {
	
	public static final String CONTENT_TYPE = "text/html";
	public static final String CHARSET = "UTF-8";


	public void init(FilterConfig fConfig) throws ServletException {

	}
	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding(CHARSET);
		// set the headers of my files
		response.setCharacterEncoding(CHARSET);
		response.setContentType(CONTENT_TYPE);
		
		chain.doFilter(request, response);
	}

	
	
	public void destroy() {
		// TODO Auto-generated method stub
	}


}
