package com.meat.yonder.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.meat.yonder.bean.PageBean;
import com.meat.yonder.bean.RoleBean;
import com.meat.yonder.dao.DaoSupport;
import com.meat.yonder.pojo.Menu;
import com.meat.yonder.pojo.Role;
import com.meat.yonder.pojo.RoleMenu;
import com.meat.yonder.pojo.RoleMenuPK;
import com.meat.yonder.service.IRoleService;
import com.meat.yonder.util.StringUtil;

@SuppressWarnings("unchecked")
@Service(value = "roleService")
public class RoleServiceImpl extends DaoSupport implements IRoleService{

	public String queryRoleName(String userId) {
		String queryHql = "select t.pk.role.name from UserRole as t where t.pk.serverUser.userId = ?";
		List<String> list = this.hibernateDao.queryList(queryHql, userId);
		String roleName = "";
		for (int i = 0; i < list.size(); i++) {
			roleName += list.get(i);
			if (i < list.size() - 1) {
				roleName += "，";
			}
		}
		return roleName;
	}
	
	public String queryRoleId(String userId) {
		String queryHql = "select t.pk.role.roleId from UserRole as t where t.pk.serverUser.userId = ?";
		List<String> list = this.hibernateDao.queryList(queryHql, userId);
		String roleId = "";
		for (int i = 0; i < list.size(); i++) {
			roleId += list.get(i);
			if (i < list.size() - 1) {
				roleId += ",";
			}
		}
		return roleId;
	}
	
	public PageBean queryRole(RoleBean roleBean) {
		String queryHql = "from Role as r where r.serverUser.userId = :userId";
		Map paramMap = new HashMap();
		paramMap.put("userId", roleBean.getUserId());
		if (!StringUtil.isEmpty(roleBean.getName())) {
			queryHql += " and r.name like :name";
			paramMap.put("name", "%"+roleBean.getName()+"%");
		}
		int allRow = this.hibernateDao.getAllRow("select count(*) " + queryHql, paramMap);
		queryHql += " order by r.createTime desc";
		List list = this.hibernateDao.queryList(queryHql, roleBean.getPage(), roleBean.getPageSize(), paramMap);
		return new PageBean(list, allRow, roleBean.getPage(), roleBean.getPageSize());
	}
	
	public List<String> getMenuIds(String roleId) {
		String queryHql = "select rm.pk.menu.menuId from RoleMenu as rm where rm.pk.role.roleId = ? order by rm.pk.menu.sort";
		return this.hibernateDao.queryList(queryHql, roleId);
	}
	
	public Role getRole(String roleId) {
		return this.hibernateDao.get(Role.class, roleId);
	}
	
	public boolean addRole(RoleBean roleBean) {
		String queryHql = "from Role as r where r.name = ? and r.serverUser.userId = ?";
		List list = this.hibernateDao.queryList(queryHql, roleBean.getName(), roleBean.getOperator().getUserId());
		if (!list.isEmpty()) {
			return false;
		}
		Role role = new Role();
		role.setCreateTime(new Date());
		role.setDescription(roleBean.getDescription());
		role.setModifyTime(new Date());
		role.setName(roleBean.getName());
		role.setServerUser(roleBean.getOperator());
		this.hibernateDao.save(role);
		String[] menuId = roleBean.getMenuIds().split(",");
		for (int i = 0; i < menuId.length; i++) {
			RoleMenu rm = new RoleMenu();
			RoleMenuPK pk = new RoleMenuPK();
			pk.setMenu(this.hibernateDao.get(Menu.class, menuId[i]));
			pk.setRole(role);
			rm.setPk(pk);
			this.hibernateDao.save(rm);
		}
		return true;
	}
	
	public boolean updateRole(RoleBean roleBean) {
		String queryHql = "from Role as r where r.name = ? and r.roleId <> ? and r.serverUser.userId = ?";
		List list = this.hibernateDao.queryList(queryHql, roleBean.getName(), roleBean.getRoleId(), roleBean.getOperator().getUserId());
		if (!list.isEmpty()) {
			return false;
		}
		Role role = this.hibernateDao.get(Role.class, roleBean.getRoleId());
		role.setDescription(roleBean.getDescription());
		role.setModifyTime(new Date());
		role.setName(roleBean.getName());
		this.hibernateDao.update(role);
		// 获取该角色绑定的权限
		queryHql = "from RoleMenu as rm where rm.pk.role.roleId = ?";
		list = this.hibernateDao.queryList(queryHql, roleBean.getRoleId());
		this.hibernateDao.deleteAll(list);
		String[] menuId = roleBean.getMenuIds().split(",");
		for (int i = 0; i < menuId.length; i++) {
			RoleMenu rm = new RoleMenu();
			RoleMenuPK pk = new RoleMenuPK();
			pk.setMenu(this.hibernateDao.get(Menu.class, menuId[i]));
			pk.setRole(role);
			rm.setPk(pk);
			this.hibernateDao.save(rm);
		}
		return true;
	}
	
	public List<Role> queryRole(String userId) {
		String queryHql = "from Role as r where r.serverUser.userId = ? order by r.name";
		return this.hibernateDao.queryList(queryHql, userId);
	}
	
}
