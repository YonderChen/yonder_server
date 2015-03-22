package com.meat.yonder.service;

import java.util.List;

import com.meat.yonder.bean.PageBean;
import com.meat.yonder.bean.ServerUserBean;
import com.meat.yonder.pojo.Menu;
import com.meat.yonder.pojo.ServerUser;


public interface IServerUserService {
	public ServerUser queryServerUser(ServerUserBean userBean, StringBuffer sb);
	public void updateServerUserLastLoginTime(ServerUser user);
	public List<Menu> queryLoginMenu(String userId);
	public PageBean queryServerUser(ServerUserBean serverUserBean);
	public ServerUser getServerUser(String userId);
	public boolean updateServerUserInfo(ServerUserBean userBean);
	public boolean addServerUser(ServerUserBean userBean);
}
