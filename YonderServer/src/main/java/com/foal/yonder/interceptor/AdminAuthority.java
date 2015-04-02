package com.foal.yonder.interceptor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.foal.yonder.config.Constant;
import com.foal.yonder.pojo.Menu;
import com.foal.yonder.pojo.ServerUser;
import com.foal.yonder.util.StringUtil;
import com.foal.yonder.web.admin.AdminBaseAction;
import com.google.common.io.BaseEncoding;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

@SuppressWarnings("unchecked")
public class AdminAuthority extends Authority {
	private static final long serialVersionUID = 595626559058339723L;
	private final Logger logger = Logger.getLogger(AdminAuthority.class);

	/**
	 * 默认构造器
	 */
	public AdminAuthority() {
		this.setAuthorityUrl("admin_login");// 设置返回的配置路径
	}

	@Override
	public boolean authority(ActionInvocation AI) {
		// 取得请求相关的ActionContext实例
		ActionContext ctx = AI.getInvocationContext();
		// 取出名为user的Session属性
		String requestUrl = ServletActionContext.getRequest().getRequestURI();
		ServerUser loginUser = (ServerUser)ctx.getSession().get(AdminBaseAction.SESSION_USERINFO_KEY);
		// 如果有登陆,通过认证
		if (loginUser != null) {
			if (requestUrl.equals(Constant.PRO_CTX_VALUE + "/web/admin/welcome") ||
					requestUrl.equals(Constant.PRO_CTX_VALUE + "/web/admin/main") ||
					requestUrl.equals(Constant.PRO_CTX_VALUE + "/web/admin/edit_pass") ||
					requestUrl.equals(Constant.PRO_CTX_VALUE + "/web/admin/edit_info")) {
				return true;
			}
			if (StringUtil.checkPassword(Constant.INIT_PASSWORD, loginUser.getEncryptedPassword(), loginUser.getAssistantPassword())) {
				logger.info("必须修改密码");
				this.setAuthorityUrl("admin_welcome");
				return false;
			}
			List<Menu> menuList = (List<Menu>)ctx.getSession().get("menuList");
			Menu menu = this.getMenuByUrl(requestUrl, menuList);
			if (menu != null) {
				String token = ServletActionContext.getRequest().getParameter("token");
				boolean identify = this.identifyToken(token, menu);
				if (!identify) {
					logger.info("访问令牌错误");
					this.setAuthorityUrl("visit_limit");
					return false;
				}
			}
			return true;
		} else {
			logger.info("登录超时,返回到登录界面");
			this.setAuthorityUrl("admin_login");
			return false;
		}
	}

	private boolean identifyToken(String token, Menu menu) {
		try {
			token = new String(BaseEncoding.base64().decode(token), "utf-8");
			String visitMenuId = this.getParameter("menuId", token);
			if (!visitMenuId.equals(menu.getMenuId())) {
				return false;
			}
			String visitKey = this.getParameter("visitKey", token);
			if (!menu.getVisitKey().equals(visitKey)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	private Menu getMenuByUrl(String requestUrl, List<Menu> menuList) {
		for (Menu menu : menuList) {
			if (requestUrl.equals(Constant.PRO_CTX_VALUE + "/" + menu.getHrefUrl())) {
				return menu;
			}
		}
		return null;
	}
	
	private String getParameter(String name, String token) {
		Matcher m = parameterPattern(name, token);
		if (m.find()) {
			return m.group(1);
		} else {
			return "";
		}
	}

	private Matcher parameterPattern(String name, String token) {
		return Pattern.compile("[&]" + name + "=([^&]*)").matcher(
				"&" + token);
	}
}
