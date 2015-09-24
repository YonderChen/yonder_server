/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yonder.game.server;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonder.game.redis.RedisCache;
import com.yonder.game.server.command.AkkaCommandRouter;
import com.yonder.game.server.command.CommandRouter;
import com.yonder.game.service.config.GameParamService;
import com.yonder.game.vo.GameParam;

public class MinaServer {
	private static final Logger logger = LoggerFactory.getLogger(MinaServer.class);
    
    private boolean isMaintained = false;//是否维护中
    
    private NioSocketAcceptor acceptor=null;
    
    private static MinaServer instance = new MinaServer();
    
    private MinaServer(){
    	
    }
    
    /**
     * 单实例
     * @return 
     */
    public static MinaServer getInstance(){
        return instance;
    }
    
    private  void initHandlerService(){
		//重新设置
		RedisCache.setMaxTryConnectTimes(Integer.MAX_VALUE);
        //恢复最大连接数
        RedisCache.defaultMaxTimes();
        //初始化服务接口注册 
        CommandRouter.initHandlerService();
        AkkaCommandRouter.initHandlerService();
    }
    
    
    private  void addLogger(DefaultIoFilterChainBuilder chain)throws Exception{
        chain.addLast("logger", new LoggingFilter());
        logger.info("Logging ON");
    }
    /**
     * 启动服务
     */
    public void start() throws  Exception{
    	int minaPort = GameParamService.getIntValue(GameParam.Param.MinaPort);
        if(isRunning()){
            logger.warn("服务已在运行中:"+minaPort);
            return;
        }
        logger.info("启动插件");        
       
        acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors()+1);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        acceptor.setReuseAddress(true);//设置的是主服务监听的端口可以重用
        acceptor.getSessionConfig().setReuseAddress(true);//设置每一个非主监听连接的端口可以重用
        //设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
        acceptor.getSessionConfig().setTcpNoDelay(true);
        //设置主服务监听端口的监听队列的最大值为100，如果当前已经有100个连接，再新的连接来将被服务器拒绝
        acceptor.setBacklog(500);//
        
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        
        // 设定这个过滤器一行一行(/r/n)的读取数据
        TextLineCodecFactory tcf = new TextLineCodecFactory();
        tcf.setDecoderMaxLineLength(1024*512); //0.5M
        tcf.setEncoderMaxLineLength(1024*512); //0.5M
        chain.addLast("myChin", new ProtocolCodecFilter(tcf));
        
        chain.addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
        addLogger(chain);
        
        //初始化处理服务 工厂类
        initHandlerService();
        //Bind
        acceptor.setHandler(new MinaServerHandler());
        
        //绑定端口，启动服务
        acceptor.bind(new InetSocketAddress(minaPort));

        logger.info("启动端口监听: "+ minaPort);

    }    
    
    /**
     * 停止服务
     */
    public void stop(){
    	logger.info("停止插件");
        if (acceptor != null) {
        	logger.info("停止服务");
            acceptor.unbind();
            acceptor.dispose();
            acceptor = null;
        }
    }
    
    /**
     * 服务是否在运行
     * @return 
     */
    public boolean isRunning(){
		if (acceptor != null && acceptor.isActive()) {
			return true;
		}
        return false;
    }

    /**
     * @return the isMaintained
     */
    public boolean isIsMaintained() {
    	boolean isExists = hasMaintainFile();
        return isMaintained || isExists;
    }
    
    private boolean hasMaintainFile(){
    	File f = new File("./maintain");
    	return f.exists();
    }

    /**
     * @param isMaintained the isMaintained to set
     */
    public void setIsMaintained(boolean isMaintained) {
        this.isMaintained = isMaintained;
    }
   
}
