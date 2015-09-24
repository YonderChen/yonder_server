/**
 * 
 */
package com.yonder.game.fix;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public abstract class FixDataService {
	protected static final Logger logger = Logger.getLogger(FixDataService.class);
	public boolean fix(){
		if(isMergeServer() && ! FixDataManage.IS_MERGE_SERVER){
			return false;
		}
		
		File f = new File("./fixDataFlags/"+getFixOkNameOfFileName());
		if(f.exists()){
			//存在已修复过的文件标识，即不再执行
			return false;
		}else{
			fixData();
			File path = new File(f.getParent());
			if(!path.exists()){
				path.mkdirs();
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
			return true;
		}
	}
	
	protected abstract void fixData();
	
	protected abstract String getFixOkNameOfFileName();
	
	/**
	 * 是否是合服补丁
	 * @return
	 */
	protected abstract boolean isMergeServer();
	
}
