package com.foal.yonder.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class UTF8EncoderFilter implements Filter {
	protected String encode;

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			if (encode != null) {
				try {
					request.setCharacterEncoding(encode);
					response.setCharacterEncoding(encode);
				} catch (java.io.UnsupportedEncodingException e) {
					request.setCharacterEncoding("UTF-8");
					response.setCharacterEncoding("UTF-8");
				}
			} else {
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		encode = filterConfig.getInitParameter("encode");
	}

}
