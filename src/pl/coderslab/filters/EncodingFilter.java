package pl.coderslab.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*@WebFilter(urlPatterns = { "/*" })*/
public class EncodingFilter implements Filter {

	public static String contentType = "text/html";
	public static String charset = "UTF-8";

	public void init(FilterConfig fConfig) throws ServletException {

		String contentTypeParam = fConfig.getInitParameter("contentType");
		if (contentTypeParam != null)
			contentType = contentTypeParam;

		String charsetParam = fConfig.getInitParameter("charset");
		if (charsetParam != null)
			charset = charsetParam;

		System.out.println("EncodingFilter initialized with " + contentTypeParam + ", " + charsetParam);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("--------EncodingFilter sets encoding and content-type");

		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		response.setContentType(contentType);

		chain.doFilter(request, response);
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

}
