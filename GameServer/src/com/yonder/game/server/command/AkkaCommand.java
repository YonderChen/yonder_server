package com.yonder.game.server.command;

import com.google.gson.JsonObject;
import com.yonder.game.service.akka.AkkaResponseCommand;

public interface AkkaCommand {
	public AkkaResponseCommand handle(JsonObject param);
    
	//管理平台 刷新缓存
	public static final short RefreshGameConfig = 9999;
	//管理平台 礼包管理
    public static final short FinishGameOrder = 9001;//完成订单，供slb与gameserver同步订单
    public static final short GET_SERVER_INFO = 9002;//获取游戏服务器负载信息
    public static final short Game1stPayManage = 9005;//首充活动管理
//    public static final short SysMsgManage = 9007;//系统消息管理
    public static final short GetGameProfileDetailForEdit = 9017;//获取玩家基本信息
    
    public static final short SendChat = 9013;//发送聊天消息，供管理员、世界服转发世界消息使用
    public static final short OperateEventInfoManage = 9014;//运营活动管理
    
    public static final short ClearMacCommand =9025;
    
    public static final short SynGameConfigFromWorldServer = 9600;
    
    public static final short LoadPlayerBattleTeam = 9015;//获取指定玩家战斗队伍成员信息
}
