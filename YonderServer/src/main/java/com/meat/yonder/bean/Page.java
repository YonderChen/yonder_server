package com.meat.yonder.bean;

import com.meat.yonder.pojo.ServerUser;

public class Page {
	private int page = 1;
	private int pageSize = 10;
	private ServerUser operator;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public ServerUser getOperator() {
		return operator;
	}

	public void setOperator(ServerUser operator) {
		this.operator = operator;
	}

}
