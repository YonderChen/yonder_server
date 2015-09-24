/**
 * 
 */
package com.yonder.game.fix;

import java.util.ArrayList;
import java.util.List;

import com.yonder.game.server.MinaServer;


public class FixDataManage {
	
	/**
	 * 合服变量
	 */
	public static boolean IS_MERGE_SERVER = false;
	
	
	public static int start(){
		MinaServer.getInstance().setIsMaintained(true);
		
		int count = 0;
		
		List<FixDataService> fixDataList = new ArrayList<FixDataService>();
//		fixDataList.add(new Fix20140520MergeService());
		
		for(FixDataService fixData : fixDataList){
			boolean result = fixData.fix();
			if (result) {
				count++;
			}
		}
		
		MinaServer.getInstance().setIsMaintained(false);
		return count;
	}

}
