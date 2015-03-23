package com.foal.yonder.imageserver.web;

import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.foal.yonder.imageserver.util.ConstUtil;
import com.foal.yonder.imageserver.util.EncrypAESUtil;

/**
 * 过滤Jersey提供的webservice接口的访问权限 1、如果是本系统用户登录，存在该用户登录的session，则允许直接访问
 * 2、如果不是本系统，并且提供正确的用户名和密码，校验通过则允许访问 3、其他情况不允许访问接口
 * 
 * @author ob.huang
 * @since 20130624
 * 
 */
public class WebSecurityFilter implements Filter
{
	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletrequest;
		//System.out.println("header value---------" +request.getHeader("token"));
		// System.out.println("请求的方法类型:" + request.getMethod());
		// System.out.println("请求的地址URL:" + request.getRequestURL());
		// 如果session取到的用户值为空，则按约定获取用户名和密码的参数(username和password)
		String username = request.getParameter("username");
		String password =request.getParameter("password");
		try
		{
			if(password!=null)
			{
				password = EncrypAESUtil.encrytToString(request.getParameter("password"));
			}
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		if (ConstUtil.USERNAME.equals(username)
				&& ConstUtil.PASSWORD.equals(password))
		{
			filterchain.doFilter(servletrequest, servletresponse);
		}
		else
		{
			//权限验证失败
			servletresponse.getWriter().print("authentication failed"); //提示发送请求的客户端权限验证失败
		}
	}

	public void init(FilterConfig filterconfig) throws ServletException
	{
		//
	}
}