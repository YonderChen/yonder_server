package com.foal.yonder.service;

import java.util.List;

import com.foal.yonder.bean.PageBean;
import com.foal.yonder.bean.ServerUserBean;
import com.foal.yonder.pojo.Menu;
import com.foal.yonder.pojo.ServerUser;


public interface IServerUserService {
	public ServerUser queryServerUser(ServerUserBean userBean, StringBuffer sb);
	public void updateServerUserLastLoginTime(ServerUser user);
	public List<Menu> queryLoginMenu(String userId);
	public List<Menu> queryAllMenu();
	public PageBean queryServerUser(ServerUserBean serverUserBean);
	public ServerUser getServerUser(String userId);
	public boolean updateServerUserInfo(ServerUserBean userBean);
	public boolean addServerUser(ServerUserBean userBean);
	public ServerUser updateServerUserPass(ServerUserBean userBean);
	public ServerUser updateServerUserBaseInfo(ServerUserBean userBean);
}
