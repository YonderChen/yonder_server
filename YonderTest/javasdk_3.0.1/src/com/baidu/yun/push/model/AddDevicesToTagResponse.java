package com.baidu.yun.push.model;

import java.util.List;
import java.util.LinkedList;
import com.baidu.yun.core.annotation.JSonPath;

public class AddDevicesToTagResponse extends PushResponse{

	@JSonPath(path="response_params\\result")
	private List<DeviceInfo> devicesInfoAfterAdded = new LinkedList<DeviceInfo> ();
	
	// get
	public List<DeviceInfo> getDevicesInfoAfterAdded () {
		return devicesInfoAfterAdded;
	}
}
