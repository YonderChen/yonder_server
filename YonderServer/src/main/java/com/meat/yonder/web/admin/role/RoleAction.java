package com.meat.yonder.web.admin.role;

import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.beans.factory.annotation.Autowired;

import com.meat.yonder.bean.AjaxBean;
import com.meat.yonder.bean.RoleBean;
import com.meat.yonder.pojo.Menu;
import com.meat.yonder.service.IRoleService;
import com.meat.yonder.service.IServerUserService;
import com.meat.yonder.web.admin.AdminBaseAction;
import com.opensymphony.xwork2.ModelDriven;

@InterceptorRefs( { @InterceptorRef("interceptor-admin") })
public class RoleAction extends AdminBaseAction implements ModelDriven<RoleBean>{
	private static final long serialVersionUID = -3113561808050453434L;

	private RoleBean roleBean = new RoleBean();
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IServerUserService serverUserService;
	
	public RoleBean getModel() {
		return roleBean;
	}

	@Action("index")
	public String index() {
		return SUCCESS;
	}
	
	@Action("list")
	public String list() {
		roleBean.setUserId(this.getSessionServerUser().getUserId());
		this.setAttrToRequest("pageBean", this.roleService.queryRole(roleBean));
		return SUCCESS;
	}
	
	@Action("add_input")
	public String addInput() {
		List<Menu> menuList = this.serverUserService.queryLoginMenu(this.getSessionServerUser().getUserId());
		this.cleanMenu(menuList);
		String menuHtml = getMenuHtml(menuList, null);
		this.setAttrToRequest("roleMenuHtml", menuHtml);
		return SUCCESS;
	}
	
	@Action("add")
	public String add() {
		roleBean.setOperator(this.getSessionServerUser());
		boolean result = this.roleService.addRole(roleBean);
		if (result) {
			ajaxBean = new AjaxBean(true, "提交成功.");
		} else {
			ajaxBean = new AjaxBean(false, "该角色名称已经存在.");
		}
		this.ajaxWrite(ajaxBean);
		return null;
	}
	
	private void cleanMenu(List<Menu> menuList) {
		// 不让admin创建的用户再创建用户
		Iterator<Menu> it = menuList.iterator();
		while (it.hasNext()) {
			Menu menu = it.next();
			if (menu.getMenuId().equals("100000")) {
				it.remove();
				continue;
			}
			if (menu.getParent() != null && menu.getParent().getMenuId().equals("100000")) {
				it.remove();
			}
		}
	}
	
	@Action("edit_input")
	public String editInput() {
		List<Menu> menuList = this.serverUserService.queryLoginMenu(this.getSessionServerUser().getUserId());
		this.cleanMenu(menuList);
		List<String> menuIds = this.roleService.getMenuIds(roleBean.getRoleId());
		String menuHtml = getMenuHtml(menuList, menuIds);
		this.setAttrToRequest("roleMenuHtml", menuHtml);
		this.setAttrToRequest("role", this.roleService.getRole(roleBean.getRoleId()));
		return SUCCESS;
	}
	
	private String getMenuHtml(List<Menu> menuList, List<String> menuIds) {
		StringBuffer sb = new StringBuffer();
		Menu parent = null;
		for (int i = 0; i < menuList.size(); i++) {
			Menu menu = (Menu)menuList.get(i);
			String checked = "";
			if (menuIds != null && menuIds.contains(menu.getMenuId())) {
				checked = "checked";
			}
			if (menu.getLevel() == 0) {
				if (parent != null) {
					sb.append("</ul></dd>");
				}
				parent = menu;
				sb.append("<dd>");
				sb.append("<div class='title'>");
				sb.append("<label class='checkbox inline'><input type='checkbox' id='"+menu.getMenuId()+"' "+checked+" onclick='selectAll(this);'/>" + menu.getText() + "</label>");
				sb.append("</div>");
				sb.append("<ul class='menuson' style='display:block;'>");
			} else if (menu.getLevel() == 1) {
				sb.append("<li><cite></cite><label class='checkbox inline'><input type='checkbox' id='"+menu.getMenuId()+"' "+checked+" onclick='selectOne(this);'/>"+menu.getText()+"</label><i></i></li>");
			}
		}
		sb.append("</ul></dd>");
		return sb.toString();
	}
	
	@Action("edit")
	public String edit() {
		roleBean.setOperator(this.getSessionServerUser());
		boolean result = this.roleService.updateRole(roleBean);
		if (result) {
			ajaxBean = new AjaxBean(true, "提交成功.");
		} else {
			ajaxBean = new AjaxBean(false, "该角色名称已经存在.");
		}
		this.ajaxWrite(ajaxBean);
		return null;
	}
}
