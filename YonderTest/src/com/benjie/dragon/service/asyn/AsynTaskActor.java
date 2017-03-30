package com.benjie.dragon.service.asyn;

import org.apache.log4j.Logger;

import akka.actor.UntypedActor;

/** 
 * @author sloanwu 
 * @version 创建时间：2014年7月9日 下午7:14:30 
 * 类说明
 * 
 */

public class AsynTaskActor extends UntypedActor {
	private static Logger logger = Logger.getLogger(AsynTaskActor.class);
	
	@Override
	public void onReceive(Object m) throws Exception {
		if(m instanceof AsynTask){			
			AsynTask task = (AsynTask)m;
			try {
				int result = task.doTask();
				getSender().tell(Integer.valueOf(result), getSelf());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			logger.debug("can't handle msg: " + m);
			unhandled(m);
		}
	}
		
}
