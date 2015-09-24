/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yonder.game.server.command;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AkkaCommandRouter {
	private static final Logger logger = LoggerFactory.getLogger(AkkaCommandRouter.class);
	
    private static Map<Short, AkkaCommand> handlerServiceMap = new HashMap<Short, AkkaCommand>();
   
    
    public static AkkaCommand getHandlerService(Short handlerId){
        if(handlerServiceMap.containsKey(handlerId)){
            return handlerServiceMap.get(handlerId);
        }else{
        	logger.error("service handler not found:serviceTag="+handlerId);
        }
        return null;
    }
    
    /**
     * 注册处理服务
     * @param handlerId
     * @param handlerService 
     */
    public static void register(short handlerId, AkkaCommand handlerService){
        if(handlerServiceMap.containsKey(handlerId)){
            logger.error("处理句柄已存在:"+handlerId+"");
            throw new IllegalStateException("HandlerService注册失败：handlerId有重复！"+handlerId);
        }
        handlerServiceMap.put(handlerId, handlerService);
        
    }
    public static void clear(){
        handlerServiceMap.clear();
    }
   
    
    
    /**
     * 初始化服务接口注册 
     */
    public static void initHandlerService(){
    	//服务接口注册
        clear();
        
    }
}
