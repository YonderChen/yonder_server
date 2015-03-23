package com.foal.yonder.imageserver.web;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 处理struts2中get请求乱码问题 的过滤器
 * @Author: lynn.chen 陈立
 * @Modified By:
 * @Date: 2012-08-31
 * @Param: o 删除的对象
 * @Return:
 * @Exception:
 */
public class CharFilter implements Filter
{

	private FilterConfig filterConfig = null;

	public void destroy()
	{
		this.filterConfig = null;
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		String encoding = this.filterConfig.getInitParameter("encoding");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse resp = (HttpServletResponse) response;
		request.setCharacterEncoding(encoding);
		resp.setContentType("text/html;charset=" + encoding);
		// System.out.println(request.getQueryString());
		// 处理GET参数
		if (request.getMethod().equalsIgnoreCase("get"))
		{
			Map paramMap = req.getParameterMap();
			String[] queryStringArray = { "" };
			if (request.getQueryString() != null)
			{
				queryStringArray = request.getQueryString().split("&");
			}
			for (int i = 0; i < queryStringArray.length; i++)
			{
				queryStringArray[i] = queryStringArray[i].replaceAll("(.*)=(.*)", "$1");
			}
			Set<String> keySet = paramMap.keySet();
			for (String key : keySet)
			{
				// check where param from
				boolean isFromGet = false;
				for (String paramFromGet : queryStringArray)
				{
					if (key.equals(paramFromGet))
					{
						isFromGet = true;
					}
				}
				if (!isFromGet)
				{
					continue;
				}
				String[] paramArray = (String[]) paramMap.get(key);
				for (int i = 0; i < paramArray.length; i++)
				{
					paramArray[i] = new String(paramArray[i].getBytes("iso-8859-1"), encoding);
				}
			}
		}
		chain.doFilter(req, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;
	}
}