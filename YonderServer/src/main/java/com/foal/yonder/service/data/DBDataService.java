package com.foal.yonder.service.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.Props;

import com.foal.yonder.log.bj.BattleErrorLog;
import com.foal.yonder.log.bj.BuyDiamondLog;
import com.foal.yonder.log.bj.CustomClientErrorLog;
import com.foal.yonder.log.bj.FirstPayLog;
import com.foal.yonder.log.bj.IDBLog;
import com.foal.yonder.service.akka.AkkaService;
import com.google.gson.JsonObject;


public class DBDataService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(DBDataService.class);
	private static DBDataService instance = new DBDataService();
	
	public enum Table{
		BattleErrorLog(new BattleErrorLog()),
		CustomClientErrorLog(new CustomClientErrorLog()),
		BuyDiamond(new BuyDiamondLog()),
		FirstPayLog(new FirstPayLog());
		
		private IDBLog log;
		
		private Table(IDBLog log){
			this.log = log;
		}

		public IDBLog getLog() {
			return log;
		}
		
	}
	
	private Map<Table, ActorRef> actorMap = new ConcurrentHashMap<Table, ActorRef>();
	
	public void start() {
		for (Table table : Table.values()) {
			ActorRef actor = AkkaService.getInstance().getActorSystem().actorOf(Props.create(DBDataCollectActor.class, table));
			actorMap.put(table, actor);
		}
	}
	
	public static DBDataService getInstance() {
		return instance;
	}
	
	public void save(Table table, JsonObject data) {
		actorMap.get(table).tell(data, ActorRef.noSender());
	}
	
	public enum Command {
		Flush
	}
	
	public void refreshCacheToDB() {
		for (Map.Entry<Table, ActorRef> kv : actorMap.entrySet()) {
			kv.getValue().tell(Command.Flush, ActorRef.noSender());
		}
	}

}
