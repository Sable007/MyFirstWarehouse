package myFilter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class myFilter implements Filter {
	
	
	private String initParameter;
	private String[] split;
	private Object attribute;
	public void init(FilterConfig filterConfig) throws ServletException {
		initParameter = filterConfig.getInitParameter("unCheckUris");
		split = initParameter.split(",");
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			HttpServletRequest req=(HttpServletRequest) request;
			HttpServletResponse resp=(HttpServletResponse) response;
			String requestUri=req.getRequestURI();
			for (String string : split) {
				if(string.equals(req.getRequestURI())){
					attribute = req.getSession().getAttribute("User");
					if(attribute==null){
						resp.sendRedirect("http://jc02.free.idcfengye.com/a1/sms");
						return;
					}
				}	
			}
			chain.doFilter(req, resp);
			
	}
	public void destroy() {}
}
