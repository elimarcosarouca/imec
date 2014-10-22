package br.com.ss.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		if (isAjaxRequest(request) && !request.isRequestedSessionIdValid()) {
			String xml = getXmlPartialResponse();
			response.getWriter().write(xml);
		} else {
			chain.doFilter(request, response);
		}
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
	
	private String getXmlPartialResponse() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\""
				+ "j_spring_security_logout?faces-redirect=true" + "\"></redirect></partial-response>";
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException { }
	
	@Override
	public void destroy() { }
	
}
