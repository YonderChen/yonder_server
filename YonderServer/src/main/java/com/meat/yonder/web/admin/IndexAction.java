package com.meat.yonder.web.admin;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.meat.yonder.bean.AjaxBean;
import com.meat.yonder.bean.ServerUserBean;
import com.meat.yonder.config.Constant;
import com.meat.yonder.pojo.Menu;
import com.meat.yonder.pojo.ServerUser;
import com.meat.yonder.service.IServerUserService;
import com.opensymphony.xwork2.ModelDriven;

public class IndexAction extends AdminBaseAction implements ModelDriven<ServerUserBean>{
	private static final long serialVersionUID = 1781250358098834474L;

	private ServerUserBean userBean = new ServerUserBean();
	
	@Autowired
	private IServerUserService serverUserService;

	public ServerUserBean getModel() {
		return this.userBean;
	}
	
	@Action("index")
	public String index() {
		return SUCCESS;
	}
	
	@Action("login")
	public String login() {
		String validationCode = (String)this.getAttrFromSession("validationCode");
		if (validationCode == null) {
			ajaxBean = new AjaxBean(false, "验证码超时.");
			this.ajaxWrite(ajaxBean);
			return null;
		} else if (!this.userBean.getCode().equalsIgnoreCase(validationCode)) {
			ajaxBean = new AjaxBean(false, "验证码错误.");
			this.ajaxWrite(ajaxBean);
			return null;
		}
		StringBuffer sb = new StringBuffer();
		ServerUser user = this.serverUserService.queryServerUser(userBean, sb);
		if (user == null) {
			ajaxBean = new AjaxBean(false, sb.toString());
			this.ajaxWrite(ajaxBean);
			return null;
		}
		this.setAttrToSession("loginLast", user.getLastLoginTime());
		user.setLogIp(this.getRequest().getRemoteAddr());
		this.setSessionServerUser(user);
		this.serverUserService.updateServerUserLastLoginTime(user);
		List<Menu> menuList = this.serverUserService.queryLoginMenu(user.getUserId());
		if (menuList.size() == 0) {
			ajaxBean = new AjaxBean(false, "权限获取失败.");
			this.ajaxWrite(ajaxBean);
			return null;
		}
		this.setAttrToSession("menuHtml", this.getMenuHtml(menuList));
		String redirectUrl = Constant.PRO_CTX_VALUE + "/web/admin/main";
		this.setAttrToSession("redirectUrl", redirectUrl);
		ajaxBean = new AjaxBean(true);
		ajaxBean.setRedirectUrl(redirectUrl);
		this.ajaxWrite(ajaxBean);
		return null;
	}
	
	private String getMenuHtml(List<Menu> menuList) {
		StringBuffer sb = new StringBuffer();
		Menu parent = null;
		for (int i = 0; i < menuList.size(); i++) {
			Menu menu = menuList.get(i);
			if (menu.getLevel() == 0) {
				if (parent != null) {
					sb.append("</ul></dd>");
				}
				parent = menu;
				sb.append("<dd>");
				sb.append("<div class='title'>");
				sb.append("<span><img src='" + Constant.PRO_CTX_VALUE + "/images/" + menu.getIcon() + "' /></span>" + menu.getText());
				sb.append("</div>");
				sb.append("<ul class='menuson'>");
			} else if (menu.getLevel() == 1) {
				sb.append("<li><cite></cite><a href='" + Constant.PRO_CTX_VALUE + "/" + menu.getHrefUrl() + "' target='"+menu.getTarget()+"'>"+menu.getText()+"</a><i></i></li>");
			}
		}
		sb.append("</ul></dd>");
		return sb.toString();
	}
	
	@Action("logout")
	public String logout() {
		this.getSession().invalidate();
		return SUCCESS;
	}
	
	@Action("identify")
	public String identify() {
		ServerUser user = (ServerUser)this.getAttrFromSession(SESSION_USERINFO_KEY);
		if (user == null) {
			ajaxBean = new AjaxBean(false);
			this.ajaxWrite(ajaxBean);
		} else {
			String redirectUrl = "";
			if (this.getAttrFromSession("redirectUrl") != null) {
				redirectUrl = this.getAttrFromSession("redirectUrl").toString();
			}
			ajaxBean = new AjaxBean(true);
			ajaxBean.setRedirectUrl(redirectUrl);
			this.ajaxWrite(ajaxBean);
		}
		return null;
	}
	
}
