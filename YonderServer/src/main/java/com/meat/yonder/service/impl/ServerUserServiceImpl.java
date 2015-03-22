package com.meat.yonder.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.meat.yonder.bean.PageBean;
import com.meat.yonder.bean.ServerUserBean;
import com.meat.yonder.config.Constant;
import com.meat.yonder.dao.DaoSupport;
import com.meat.yonder.pojo.Menu;
import com.meat.yonder.pojo.Role;
import com.meat.yonder.pojo.ServerUser;
import com.meat.yonder.pojo.UserRole;
import com.meat.yonder.pojo.UserRolePK;
import com.meat.yonder.service.IServerUserService;
import com.meat.yonder.util.StringUtil;

@SuppressWarnings("unchecked")
@Service(value = "serverUserService")
public class ServerUserServiceImpl extends DaoSupport implements IServerUserService{

	public ServerUser queryServerUser(ServerUserBean userBean, StringBuffer sb) {
		String queryHql = "from ServerUser as t where t.username = ? and t.isDelete = 0";
		List list = this.hibernateDao.queryList(queryHql, userBean.getUsername());
		if (list.isEmpty()) {
			sb.append("用户名不存在.");
			return null;
		}
		ServerUser user = (ServerUser)list.get(0);
		if (!StringUtil.checkPassword(userBean.getPassword(), user.getEncryptedPassword(), user.getAssistantPassword())) {
			sb.append("密码错误.");
			return null;
		}
		return user;
	}
	
	public void updateServerUserLastLoginTime(ServerUser user) {
		user.setLastLoginTime(new Date());
		this.hibernateDao.update(user);
	}
	
	public List<Menu> queryLoginMenu(String userId) {
		String queryHql = "select distinct(t2.pk.menu) from RoleMenu as t2 where t2.pk.role.roleId in " +
		"(select t3.pk.role.roleId from UserRole as t3 where t3.pk.serverUser.userId = ?) order by t2.pk.menu.sort";
		return this.hibernateDao.queryList(queryHql, userId);
	}
	
	public PageBean queryServerUser(ServerUserBean serverUserBean) {
        String queryHql = "from ServerUser as s where s.parent.userId = :userId and s.isDelete = 0";
        Map paramMap = new HashMap();
        paramMap.put("userId", serverUserBean.getUserId());
        if (!StringUtil.isEmpty(serverUserBean.getName())) {
            queryHql += " and s.name like :name";
            paramMap.put("name", "%" +serverUserBean.getName()+"%" );
        }
        if (!StringUtil.isEmpty(serverUserBean.getUsername())) {
            queryHql += " and s.username like :username";
            paramMap.put("username", "%" +serverUserBean.getUsername()+"%" );
        }
        if (!StringUtil.isEmpty(serverUserBean.getPhone())) {
            queryHql += " and s.phone like :phone";
            paramMap.put("phone", "%" +serverUserBean.getPhone()+"%" );
        }
        int allRow = this.hibernateDao.getAllRow("select count(*) " + queryHql, paramMap);
        queryHql += " order by s.createTime desc";
        List list = this.hibernateDao.queryList(queryHql, serverUserBean.getPage(), serverUserBean.getPageSize(), paramMap);
        return new PageBean(list, allRow, serverUserBean.getPage(), serverUserBean.getPageSize());
    }

	public ServerUser getServerUser(String userId) {
		return this.hibernateDao.get(ServerUser.class, userId);
	}
	
	public boolean updateServerUserInfo(ServerUserBean userBean) {
		ServerUser user = this.getServerUser(userBean.getUserId());
		if (user == null) {
			return false;
		}
		user.setName(userBean.getName());
		user.setPhone(userBean.getPhone());
		user.setModifyTime(new Date());
		this.hibernateDao.update(user);
		List list = this.hibernateDao.queryList("from UserRole as u where u.pk.serverUser.userId = ?", userBean.getUserId());
		this.hibernateDao.deleteAll(list);
		String[] roleId = userBean.getRoleIds().split(",");
		for (int i = 0; i < roleId.length; i++) {
			UserRole ur = new UserRole();
			UserRolePK pk = new UserRolePK();
			pk.setRole(this.hibernateDao.get(Role.class, roleId[i]));
			pk.setServerUser(user);
			ur.setPk(pk);
			this.hibernateDao.save(ur);
		}
		return true;
	}

	public boolean addServerUser(ServerUserBean userBean) {
		String queryHql = "from ServerUser as t where t.username = ? and t.isDelete = 0";
		List list = this.hibernateDao.queryList(queryHql, userBean.getUsername());
		if (!list.isEmpty()) {
			return false;
		}
		ServerUser user = new ServerUser();
		user.setUsername(userBean.getUsername());
		user.setName(userBean.getName());
		user.setPhone(userBean.getPhone());
		user.setModifyTime(new Date());
		user.setParent(userBean.getOperator());
		user.setCreateTime(new Date());
		String[] passwords = StringUtil.generatePassword(Constant.INIT_PASSWORD);
		user.setAssistantPassword(passwords[1]);
		user.setEncryptedPassword(passwords[0]);
		this.hibernateDao.save(user);
		String[] roleId = userBean.getRoleIds().split(",");
		for (int i = 0; i < roleId.length; i++) {
			UserRole ur = new UserRole();
			UserRolePK pk = new UserRolePK();
			pk.setRole(this.hibernateDao.get(Role.class, roleId[i]));
			pk.setServerUser(user);
			ur.setPk(pk);
			this.hibernateDao.save(ur);
		}
		return true;
	}
}
