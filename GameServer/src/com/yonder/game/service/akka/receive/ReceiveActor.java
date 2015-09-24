package com.yonder.game.service.akka.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;

import com.yonder.game.exception.GameException;
import com.yonder.game.redis.RedisCache;
import com.yonder.game.server.MinaServer;
import com.yonder.game.server.command.AkkaCommand;
import com.yonder.game.server.command.AkkaCommandRouter;
import com.yonder.game.service.akka.AkkaRequestCommand;
import com.yonder.game.service.akka.AkkaResponseCommand;
import com.yonder.game.tools.GsonTools;

public class ReceiveActor extends UntypedActor {

	private static final Logger logger = LoggerFactory.getLogger(ReceiveActor.class);
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof AkkaRequestCommand) {
			AkkaRequestCommand request = (AkkaRequestCommand) msg;
			try {
				if(MinaServer.getInstance().isIsMaintained()) {
		            AkkaResponseCommand response = new AkkaResponseCommand();
					response.setTag(request.getTag());
					response.setCode(AkkaResponseCommand.Code.Maintain);
					response.setMsg("服务器维护中，请稍后！");
					this.getSender().tell(response, getSelf());
		            return;
		        }
				AkkaCommand service = AkkaCommandRouter.getHandlerService(request.getTag()); 
                if(service == null){
                	AkkaResponseCommand response = new AkkaResponseCommand();
 					response.setTag(request.getTag());
 					response.setCode(AkkaResponseCommand.Code.Fail);
 					response.setMsg("command not found【"+request.getTag()+"】.");
 					this.getSender().tell(response, getSelf());
 		            return;
                }
                AkkaResponseCommand response = service.handle(GsonTools.parseJsonObject(request.getBody()));
                response.setTag(request.getTag());
				this.getSender().tell(response, getSelf());
			} catch (GameException me) {
				me.printStackTrace();
				logger.error(me.getMessage(), me);
				AkkaResponseCommand response = new AkkaResponseCommand();
				response.setTag(request.getTag());
				response.setCode(me.getStatusCode());
				response.setMsg(me.getStatusMsg());
				this.getSender().tell(response, getSelf());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				AkkaResponseCommand response = new AkkaResponseCommand();
				response.setTag(request.getTag());
				response.setCode(AkkaResponseCommand.Code.Fail);
				response.setMsg(e.getMessage());
				this.getSender().tell(response, getSelf());
			} finally {
				RedisCache.closeCacheConnection();
			}
		} else {
			AkkaResponseCommand response = new AkkaResponseCommand();
			response.setCode(AkkaResponseCommand.Code.Fail);
			response.setMsg(msg.toString());
			this.getSender().tell(response, getSelf());
			logger.info(msg.toString());
		}
	}
}
