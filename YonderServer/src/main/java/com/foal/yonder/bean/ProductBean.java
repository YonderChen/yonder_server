package com.foal.yonder.bean;

public class ProductBean extends Page {
	private String name;
	private String shopId;
	private String productIds;
	private String productIdCounts;
	private String logId;
	private String submitTimeFrom;
	private String submitTimeTo;
	private String userId;
	private int type;
	private String orderId;
	private String productId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public String getProductIdCounts() {
		return productIdCounts;
	}

	public void setProductIdCounts(String productIdCounts) {
		this.productIdCounts = productIdCounts;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getSubmitTimeFrom() {
		return submitTimeFrom;
	}

	public void setSubmitTimeFrom(String submitTimeFrom) {
		this.submitTimeFrom = submitTimeFrom;
	}

	public String getSubmitTimeTo() {
		return submitTimeTo;
	}

	public void setSubmitTimeTo(String submitTimeTo) {
		this.submitTimeTo = submitTimeTo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
