package com.foal.yonder.bean;

import java.util.List;

public class PageBean {
	private List list;
	private int allRow;
	private int totalPage;
	private int currentPage;
	private int pageSize;
	
	public PageBean() {
		
	}
	
	public PageBean(List list, int allRow, int currentPage, int pageSize) {
		this.list = list;
		this.allRow = allRow;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getAllRow() {
		return allRow;
	}

	public void setAllRow(int allRow) {
		this.allRow = allRow;
	}

	public int getTotalPage() {
		totalPage = (allRow % pageSize == 0) ? (allRow / pageSize) : (allRow / pageSize + 1);
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int[] getPageSizes() {
		int[] pageSizes = new int[6];
		pageSizes[0] = 10;
		pageSizes[1] = 20;
		pageSizes[2] = 30;
		pageSizes[3] = 40;
		pageSizes[4] = 50;
		pageSizes[5] = 100;
		return pageSizes;
	}
	
	public int[] getPages() {
		int[] pages = new int[this.getTotalPage()];
		for (int i = 1; i <= pages.length; i++) {
			pages[i - 1] = i;
		}
		return pages;
	}

}