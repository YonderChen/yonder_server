/**
 * 
 */
package com.yonder.game.parameter;

public class RedisKey {

	private static final String PrefixPlayerDayCountKey = "playerDailyCounts";
	private static final String PrefixPlayerMonthCountKey = "playerMonthlyCounts";
	
	/**
	 * 每日计数器
	 * 
	 * @return
	 */
	public static String getPlayerDayCountKey(long day) {
		return PrefixPlayerDayCountKey + ":" + day;
	}

	/**
	 * 每月计数器
	 * 
	 * @return
	 */
	public static String getPlayerMonthCountKey(int month) {
		return PrefixPlayerMonthCountKey + ":" + month;
	}

	
	/**
	 * 世界服同步配置文件MD5加密
	 */
	public static final String GameConfigWorldServerCheckSign = "gameConfigWorldServerCheckSign";
	
	//=====================================邮件=====================================
	/**
	 * 全服邮件
	 */
	public final static String PublicPlayerMsgKey = "publicGameMsg";
	/**
	 * 发送给非好友次数
	 */
	public final static String SendMsgToStrangerCount = "sendMsgToStrangerCount";
	//=====================================邮件=====================================end

	
	//=====================================精灵召唤=====================================
	private final static String PreSummonCount = "summonCount";//金币召唤总次数
	public static String getSummonCountKey(int type) {
		return PreSummonCount + ":" + type;
	}
	public final static String PreLastFreeSummonTime = "lastFreeSummonTime";//上一次免费金币召唤时间
	public static String getLastFreeSummonTime(int type, int dropNum) {
		return PreLastFreeSummonTime + ":" + type + ":" + dropNum;
	}
	//=====================================精灵召唤=====================================end
	
	
	//=====================================好友=====================================
	/**
	 * 好友关系
	 */
	public static final String GameProfileFriend = "gpFriend";
	//=====================================好友=====================================end

	
	//=====================================竞技场=====================================
	/**
	 * 竞技场排名
	 */
	public static final String ArenaRankKey = "arenaRank";
	
	/**
	 * 竞技场奖励
	 */
	public static final String ArenaRewardKey = "arenaReward";
	
	/**
	 * 竞技场奖励领取标识
	 */
	public static final String ArenaRewardReceiveFlag = "arenaRewardRecvFlag";
	
	/**
	 * 竞技场历史最高排名
	 */
	public static final String ArenaHistoryTopRank = "arenaHistoryTopRank";
	
	/**
	 * 竞技场挑战失败冷却时间 
	 */
	public final static String ArenaBattleRecoverTime = "arenaFailRecoverTime";
	//=====================================竞技场=====================================end

	
	//=====================================公会=====================================
	/**
	 * 公会信息
	 * 
	 * @param gpId
	 * @return
	 */
	public static String getSocietyInfo(String societyId) {
		return "society:" + societyId;
	}

	/**
	 * 某个公会申请信息 hash:field gpId value:apply time(unixtime)
	 * 
	 * @param societyId
	 * @return
	 */
	public static String getSocietyApply(String societyId) {
		return "society:apply:" + societyId;
	}

	/**
	 * 某个人的公会申请信息 
	 * 
	 * @param societyId
	 * @return
	 */
	public static String getPlayerSocietyApply(String gpId) {
		return "society:player:apply:" + gpId;
	}

	/**
	 * 公会贡献
	 * @param societyId
	 * @return
	 */
	public static String getSocietyDevote(String societyId) {
		return "society:devote:" + societyId;
	}

	/**
	 * 公会成员 hash field gpId value createTime(unixtime)
	 * 
	 * @param societyId
	 * @return
	 */
	public static String getSocietyMember(String societyId) {
		return "society:member:" + societyId;
	}

	private static final String SocietyMemberRankPrefix = "society:memberRank:";
	
	/**
	 * 公会成员内部排名 zset score:fame member:gpid
	 * 
	 * @param societyId
	 * @return
	 */
	public static String getSocietyMemberRank(String societyId) {
		return SocietyMemberRankPrefix + societyId;
	}
	
	public final static String LastEixtSocietyTime = "lastEixtSocietyTime";//上一次退出公会时间
	
	public final static String SocietyRainbowFruit = "societyRainbowFruit";//公会彩虹果信息
	
	public final static String SocietySealInfo = "societySealInfo";//公会封印魔物信息
	
	public final static String SocietyDarkInvasionInfo = "societyDarkInvasionInfo";//公会黑暗侵袭信息
	
	public final static String SocietyBossInfo = "societyBossInfo";//公会boss信息
	
	public final static String SocietyBattleInfo = "societyBattleInfo";//公会战信息
	
	public final static String SocietyBattleBadageRank = "societyBattleBadageRank";//公会战徽章排行
	
	public final static String SocietyBattleStage = "societyBattleStage";//公会战当前阶段
	//=====================================公会=====================================end
	
	//=====================================商店=====================================
	/**
	 * 杂货商店信息key
	 * 
	 * @param gpId
	 * @return
	 */
	public static String getShopInfoKey(int shopType) {
		return "shopInfo:" + shopType;
	}
	//=====================================商店=====================================end

	//=====================================英雄副本=====================================
	/**
	 * 获取属系随机英雄副本菜单id的key
	 */
	public static final String NatureHeroStoryIds = "natureHeroStoryIds";
	/**
	 * 获取属系随机英雄副本菜单id今天随机到的属系值的key
	 */
	public final static String NatureHeroStoryTodayNatureMenuId = "natureHeroStoryTodayNatureMenuId";
	/**
	 * 获取属系随机英雄副本菜单id的key
	 */
	public static String getRandomHeroStoryKey(int id) {
		return "randomHeroStory:" + id;
	}
	//=====================================英雄副本=====================================end

	//=====================================成就系统、主线任务=====================================
	
	public static final String AchieveRewardReceived = "achieveRewardReceived";
	
	/**
	 * 玩家配方任务，成就系统，主线任务
	 * 
	 * @param gpId
	 * @return
	 */
	public static String getOperateTask(String gpId) {
		return "operate:task:" + gpId;
	}

	/**
	 * 玩家主线任务初始值
	 * 
	 * @param gpId
	 * @return
	 */
	public static final String MainlineTaskInitKey = "mainlineTaskInitKey";
	//=====================================成就系统、主线任务=====================================end

	//=====================================每日任务=====================================
	private static final String PrefixPlayerDayTaskKey = "playerDailyTasks";
	
	/**
	 * 每日任务
	 * 
	 * @param day
	 * @return
	 */
	public static String getPlayerDayTaskKey(long day) {
		return PrefixPlayerDayTaskKey + ":" + day;
	}
	//=====================================每日任务=====================================end

	//=====================================探险=====================================
	private static final String PrePlayerExploreSceneKey = "playerExploreSceneKey";

	/**
	 * 获取某个玩家某个场景的事件id
	 */
	public static String getPlayerExploreSceneKey(int exploreType) {
		return PrePlayerExploreSceneKey + ":" + exploreType;
	}
	
	public static final String PlayerLockedByEvilWitchKey = "playerLockedByEvilWitchKey";
	//=====================================探险=====================================end


	//=====================================运营活动=====================================
	/**
	 * 运营活动事件信息 map:
	 */
	public final static String OperateEvents = "operateEvents";
	/**
	 * 运营活动MD5加密
	 */
	public static final String EventInfoCheckSign = "eventInfoCheckSign";
	/**
	 * 运营/折扣活动更新
	 */
	public final static String EventStatusChange = "eventStatusChange";
	/**
	 * 运营活动信息记录
	 * 
	 */
	public final static String OperateEventRecord = "operateEventRecord";
	
	/**
	 * 首冲
	 */
	public static final String BeginOf1stPay = "beginOf1stPay";
	public static final String EndOf1stPay = "endOf1stPay";
	//=====================================运营活动=====================================end

	//=====================================临时仓库=====================================end
	/**
	 * 临时仓库
	 */
	public static final String TempBagData = "tempBagData";
	//=====================================临时仓库=====================================end

	//=====================================战斗力=====================================end
	/**
	 * 历史最强战斗力队伍的战斗力值
	 */
	public static final String MaxBattleCombat = "maxBattleCombat";
	/**
	 * 历史最强战斗力队伍
	 */
	public static final String MaxBattleCombatTeam = "maxBattleCombatTeam";
	//=====================================战斗力=====================================end

	//=====================================暗影岛=====================================end
	/**
	 * 暗影岛玩家信息
	 */
	public static final String ShadowIslandInfo = "shadowIslandInfo";
	//=====================================暗影岛=====================================end

	//=====================================战斗=====================================end
	/**
	 * 战斗缓存临时数据（服务关闭时将缓存数据写入redis，服务启动从redis加载）
	 */
	public static final String BattleCacheData = "battleCacheData";
	//=====================================战斗=====================================end
	
	//=====================================黑水财团=====================================end
	/**
	 * 护送的玩家
	 */
	public static final String ConsortiumConvoyRival = "consortiumConvoyRival";
	
	/**
	 * 玩家的护送信息
	 * @param gpId
	 * @return
	 */
	public static final String ConsortiumConvoyInfo = "consortiumConvoyInfo";
	/**
	 * 玩家的被袭击和护送成功奖励日志
	 * @param gpId
	 * @return
	 */
	public static String getConsortiumLog(String gpId){
		return "consortium:log:"+gpId;
	}
	
	public static String getNewLogSign(String gpId){
		return "consortium:log:sign:"+gpId;
	}
	
	//=====================================黑水财团=====================================end
	
	//=====================================体力和金币购买信息=====================================end
	public static String getBuyInfoKey(int type){
		return "buyInfo:"+type;
	}
	//=====================================体力和金币购买信息=====================================end
}
