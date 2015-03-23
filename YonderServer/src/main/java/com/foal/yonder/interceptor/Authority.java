package com.foal.yonder.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.log4j.Logger;

public abstract class Authority extends AbstractInterceptor {
	private static final long serialVersionUID = -7207467066748069421L;
	private final Logger logger = Logger.getLogger(Authority.class);
	
	private String authorityUrl = "login";
	
	public String getAuthorityUrl() {
		return authorityUrl;
	}

	public void setAuthorityUrl(String authorityUrl) {
		this.authorityUrl = authorityUrl;
	}

	@Override
	public String intercept(ActionInvocation AI) throws Exception {
		logger.debug("请求调用路径:" + AI.getProxy().getConfig().getName()
				+ "==>调用类:" + AI.getProxy().getConfig().getClassName()
				+ "==>方法:" + AI.getProxy().getConfig().getMethodName());
		if (authority(AI)) {
			try {
				return AI.invoke();
			} catch (Exception e) {
				logger.info("异常信息:", e);
				throw e;
			}
		} else {
			logger.info("认证未通过,请求被拒绝！-返回指定的默认URL:" + this.getAuthorityUrl());
			return this.getAuthorityUrl();
		}
	}
	
	public abstract boolean authority(ActionInvocation AI);
	
}
