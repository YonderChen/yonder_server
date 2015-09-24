/**
 * 
 */
package com.yonder.game.parameter;

public class DataKeys {
	public static final String GameProfileId = "gpId";
	public static final String StatusCode = "sc";
    public static final String StatusMsg = "er";
	public static final String Day = "d";//所有接口返回,是否有同一天，天数long
	public static final String TimeNow = "tc";//服务器的时间戳
    public static final String IsUpdateMac = "isUpdateMac";
    public static final String LoginId = "loginId";
	public static final String AreaId = "areaId";
	public static final String GameProfile = "gameProfile";
    public static final String RewardInfo = "rewardInfo";
    public static final String RewardExps = "rewardExps";
    public static final String CostInfo = "costInfo";
    public static final String CostHeroEquipments = "costHeroEquipments";
    public static final String HeroExp = "heroExp";
    public static final String EidolonExp = "eidolonExp";
    public static final String BattleParam = "battleParam";
	public static final String PlayerMsg = "msgs";//推送的消息，会随时出现在所有的接口中
    public static final String Chat = "chat";//聊天消息
    public static final String SwitchOf1stPay ="sp";//首充活动开关,1是开启，0是关闭
    public static final String AddHeroEquipments = "addHeroEquipments";//添加的主角装备列表    
    public static final String DailyTask = "dailyTask";
    public static final String DailyTaskValue = "dtv";//

    public static final String Pp = "pp";
    
    public static final String Name = "name";
    public static final String Icon = "icon";
    public static final String Level = "level";
    public static final String BattleCombat = "battleCombat";
    public static final String ArenaRank = "arenaRank";
    public static final String UpdateAt = "updataAt";
    public static final String FromLastUpdateTime = "fromLastUpdateTime";
    public static final String Mac = "mac";
    
    public static final String Data = "data";
	public static final String Timestamp = "timestamp";
	
	public static final String IsGuide = "isGuide";
	public static final String GuideId = "guideId";
	public static final String GuideStep = "guideStep";
	public static final String GuideIsEnd = "guideIsEnd";

	public static final class BattleKeys {
    	public static final String BattleResult = "battleResult";//战斗结果数据
    	public static final String IsWin = "isWin";//战斗结果数据
    }
    public static final class Society{
    	public static final String Id = "id";
    	public static final String Name = "name";
    	public static final String Logo = "logo";
    	public static final String LogoColor= "logoColor";
    	public static final String Des = "des";
    	public static final String Count = "count";
    	public static final String MaxCount= "maxCount";
    	public static final String Exp = "exp";
    	public static final String Members = "members";
    	public static final String Society = "society";
    }
    public static final class SocietyMember{
    	public static final String GpId = GameProfileId;
    	public static final String Type = "type";
    	public static final String Icon = "icon";
    	public static final String Name = "name";
    	public static final String Level = "level";
    	public static final String LastTime = "lastTime";//
		public static final String Devote = "devote";
		public static final String Rank = "rank";
    }
    
}
