/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yonder.game.exception;

public class GameException extends IllegalStateException {
	private static final long serialVersionUID = -6274605634030627362L;
	private int statusCode;
	private String statusMsg;

	public GameException(int statusCode, String msg) {
		super(msg);
		this.statusCode = statusCode;
		this.statusMsg = msg;
	}

	public GameException(String msg) {
		super(msg);
		this.statusMsg = msg;
	}
	
	public GameException(int statusCode) {
		this(statusCode, "");
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the statusMsg
	 */
	public String getStatusMsg() {
		return statusMsg;
	}

	/**
	 * @param statusMsg
	 *            the statusMsg to set
	 */
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	/**
	 * 返回报文的状态码常量 1000以上为固定意义的常量
	 */
	public static final int MAINTAINING = -1;// 系统维护中
	public static final int UnKnowError = -1;// 未知错误
	public static final int ParamError = -2;// 参数错误
	public static final int LoginInOtherPlace = -3;// 在其他地方登录
	public static final int ForceReLogin = -4;// 强制重新登录
	public static final int RequestTooFast = -5;// 同一玩家请求过于频繁
	public static final int NotAllowClientIp = -6;// 未许可的客户端访问
	public static final int NotGpIdCanUsed = -7;// 角色id已用完
	public static final int SignErrorGlobal = -8;// 签名错误
	public static final int AccountIsBan = -9;// 游戏账号被禁用
	public static final int ConfigError = -10;// 策划数据配置错误
	public static final int DataBaseError = -11;// 数据库操作错误
	public static final int UncompressError = -12;// 数据包解压错误

	public static final int JDBCException = 101;//  数据库操作异常，事务回滚
	
	public static final int ErrorState = 105;// 状态不正确
			
	public static final int NotEnoughBagNum = 1001;// 虚拟背包里面的物品数目不够。
	public static final int DataIsNew = 1003;// 数据是最新的，无需更新
	
	public static final int RewardTaskHadReceived = 1023;// 领取失败，奖励已领取过

	public static final int PlayerDataIsNotNew = 1047;// 玩家数据不同步
	
	public static final int NotExistedLoginId = 1049;// 指定的loginId不存在
	public static final int NotExistedGpId = 1050;// 指定的gpId玩家不存在
	//用户
	public static final int PlayerNameHadExist = 1101;// 名字已存在
	public static final int PlayerNameExistRisk = 1102;// 名字存在敏感词
	public static final int PlayerNameLenghNotFit = 1103;// 名字长度不符合规范
	public static final int CreatePlayerExists = 1104;// 玩家已经注册成功
	public static final int PlayerIconIsLock = 1105;//头像未解锁
	public static final int PlayerLevelNotFit = 1106;//玩家等级不足
	
	//家园建筑
	public static final int HomeBuildingNodeIsOccupied = 1201;//建筑物覆盖的位置已经被占用
	public static final int AddHomeBuildingExist = 1202;//添加的建筑物已存在
	public static final int HomeBuildingNotExist = 1203;//建筑物列表中没有该建筑物
	public static final int RemovePermanentBuilding = 1204;//移除不可移除的建筑
	public static final int LandNotOpen = 1205;//地块未开启
	public static final int LandIsOpened = 1206;//地块已经开启
	public static final int HomeBuildNumMax = 1207;//建筑物数目已经达到上限
	public static final int AddHomeBuildingIsNotOpen = 1208;//建筑功能没有开放，不能建造

	//精灵
	public static final int EidolonEquipmentExist = 1301;//已经装备过该物品
	public static final int EidolonEquipmentNotFit = 1302;//装备与精灵不匹配
	public static final int NotEnoughStarEidolon = 1304;//指定星级精灵数量不足
	public static final int EidolonNotExistById = 1305;//找不到指定id的精灵
	public static final int EidolonLevelNotFit = 1306;//精灵等级不足
	public static final int EidolonRuneAdvanceStepMax = 1307;//找不到下一个阶精灵符文配置
	public static final int EidolonAdvanceStarMax = 1308;//精灵星级已经达到最高等级
	public static final int EidolonSkillLevelMax = 1309;//精灵星级已经达到最高等级
	public static final int EidolonLevelMax = 1310;//精灵星级已经达到最高等级
	public static final int EidolonExpMaterialNotFit = 1312;//精灵经验材料不匹配
	public static final int EidolonExist = 1313;//已经拥有该精灵
	public static final int UnKnowSummonType = 1314;//未知的召唤类型
	public static final int NotEnoughFreeSummonTimes = 1315;//免费召唤次数已用完
	public static final int EidolonRuneNotExist = 1316;//符文不存在
	
	//副本
	public static final int StoryIsLock = 1401;//副本未解锁
	public static final int StoryNotAvailableCount = 1402;//副本通关次数已经用完
	public static final int StoryIsNotOpen = 1403;//当前该副本没有开放
	public static final int RandomStoryIsChange = 1404;//随机副本已经变更
	public static final int RandomStoryIndexNotOpen = 1405;//指定位置的随机副本不存在
	public static final int StoryIsNotExist = 1406;//指定副本不存在
	public static final int HasAvailableStoryBattleNum = 1407;//副本通关可用次数未用完
	public static final int NotEnoughBuyStoryBattleCount = 1408;//副本通关无可购买次数
	
	//战斗
	public static final int BattleCheckFaild = 1501;//战斗验证失败

	//好友
	public static final int HasBeenFriend = 2000;// 已是好友关系,发送好友请求处理时可能抛出
	public static final int HasSendFriendRequest = 2001;// 已发送过好友请求，
	public static final int NoExistedPlayer = 2002;// 玩家不存在
	public static final int RiskContent = 2003;// 敏感词
	public static final int NotEnoughFriendCount = 2004;// 好友数目已达上限
	public static final int NotEnoughFetchPpFromFriendTime = 2005;// 领取好友体力每日领取次数已达上限
	public static final int NotEnoughSendPpForSameFriend = 2006;// 同一好友，每日只能赠送一次体力
	public static final int NotEnoughPpCanBeFetch = 2007;// 没有可用的体力可领取
	public static final int NoAllowSendType = 2008;// 未允许发送消息的类型
	public static final int CanNotAddSelf = 2009;// 不能添加自己为好友
	public static final int NoExistedFriend = 2010;// 好友关系已不存在
	public static final int NameNoExisted = 2011;// 该姓名不存在

	// 游戏活动
	public static final int EventNoExisted = 2800;// 活动不存在或已结束
	public static final int EventFetchedError = 2801;// 未达到或已领取过
	public static final int EventNotCorrectType = 2802;// 不是排行类活动
	
	public static final int ContentTooLong = 3000;// 内容过长
	
	// 公会
	public static final int SocietyNameRiskWord = 3001;// 公会名字中含有敏感词
	public static final int SocietyDesRiskWord = 3002;// 公会描述中含有敏感词
	public static final int AlreadyHasSociety = 3003;// 已加入1个公会，无法加入其他的
	public static final int SocietyMemberNumLimit = 3004;// 公会人数已满
	public static final int SocietyNoPermit = 3005;// 无权限操作（公会）
	public static final int SocietyViceLordNumFull = 3006;// 副会长人数已满
	public static final int SocietyNameExisted = 3007;// 公会名字已被用过
	public static final int SocietyNameLengthNotFit = 3008;// 公会名字长度不规范
	public static final int SocietyDesTooLong = 3009;// 公会简介过长
	public static final int SocietyNotExits = 3010;// 指定公会id不存在
	public static final int SocietyNotOpenByPlayerLevel = 3011;// 等级不足，公会模块还未开放
	public static final int SocietyNotEmpty = 3012;// 公会还有其他成员，会长不得退出公会
	public static final int SocietyExitNotRecover = 3013;// 上一次退出公会冷却时间未结束
	public static final int SocietyNoticeTooLong = 3014;// 公会公告过长
	public static final int SocietyApplyIsCancel = 3015;// 申请已经被取消
	public static final int SocietyDevoteCountNotEnough = 3016;// 公会捐献次数已用完
	public static final int TargetPlayerNotInTheSameSociety = 3017;// 目标玩家与您不再同一个公会
	public static final int SocietyWorshipEncourageCountNotEnough = 3018;//膜拜、激励次数已用完
	public static final int SocietyHasWorshippedTargetGpId = 3019;//今天已经膜拜过目标用户
	public static final int SocietyHasEncouragedTargetGpId = 3020;//今天已经激励过目标用户
	public static final int SocietyPickRainbowFruitCountNotEnough = 3021;//今天采摘彩虹果实次数已用完
	public static final int SocietyRainbowFruitInfoIsUpdate = 3022;//彩虹果实数据已更新
	public static final int SocietyDarkInvasionMonsterAttackedByOthers = 3023;//其他成员正在与怪物战斗
	public static final int SocietyDarkInvasionBattleCountNotEnouth = 3024;//没有可以战斗的次数
	public static final int SocietyDarkInvasionMonsterNotExist = 3025;//要攻打的怪物不存在
	public static final int SocietyBossBattleNotOpen = 3026;//公会boss活动未开启
	public static final int SocietyBossBattleCanNotOpenNow = 3027;//公会现在不再开放时间内，不能开启
	public static final int SocietyLevelNotFit = 3028;//公会等级不足
	public static final int SocietyBossBattleHadOpened = 3030;//今天的公会boss已经开启
	public static final int SocietyBattleCanNotSign = 3031;//公会战不能报名
	public static final int SocietyBattleNotSign = 3032;//公会战没有报名
	public static final int SocietyBattleCountNotEnough = 3033;//公会战战斗次数已用完
	public static final int SocietyBattleNotInBattleTime = 3034;//公会战不再可战斗时间内
	public static final int SocietyBattleCityDefendPlayerCountMax = 3035;//公会战城池防守人员上限
	public static final int SocietyBattleCityNotExist = 3036;//公会战城池不存在
	public static final int SocietyBattleNotInSignTime = 3037;//公会战报名时间已过，不能再进行布置防守阵容
	
	//主角
	public static final int HeroSuitCategoryExist = 4001;//该类型装备已存在
	public static final int UnknownHeroSuitCategory = 4002;//未知的类型装备
	public static final int HeroSuitCategoryNotExist = 4003;//没有该类型装备
	public static final int HeroAdvanceStarMax = 4004;//主角星级已经达到最高等级
	public static final int HeroAdvanceStarLevelNotFit = 4005;//主角未到可以升星的等级
	public static final int HeroSuitCategoryNotFit = 4006;//装备类型不符合
	public static final int HeroLevelNotFit = 4007;//主角等级不符合
	public static final int HeroEquipmentStepMax = 4008;//主角装备已经强化到最大等级（或者已经到达主角等级）
	public static final int HeroEquipmentNotExist = 4009;//主角装备不存在
	public static final int HeroFuseDiffSuit = 4010;//主角融合的装备所属套装不同
	
	//布阵
	public static final int TeamNotExist = 4101;//队伍不存在
	public static final int TeamMemberRepeat = 4102;//队伍成员重复
	public static final int TeamMemberNumNotFit = 4103;//队伍成员数不符合
	public static final int TeamMemberNotHero = 4104;//队伍中没有英雄
	public static final int TeamMemberNotExist = 4105;//队伍中的成员不存在
	public static final int TeamMemberPosRepeat = 4106;//队伍成员位置重复
	
	//签到
	public static final int AlreadySignedToday = 4201;//今天已经签过到了
	
	//时光圣地
	public static final int EidolonHasGuardBuilding = 4301;//精灵已经驻守在其他建筑了
	public static final int IsNotShrineBuilding = 4302;//该建筑不是时光圣地
	public static final int ShrinePosNotOpen = 4303;//时光圣地开启的位置数不足
	public static final int ShrinePosHasNotEidolon = 4304;//时光圣地的位置上没有精灵
	
	//竞技场
	public static final int ArenaStatRunning = 4401;//竞技场结算中
	public static final int HasAvailableBattleArenaNum = 4402;//战斗可用次数未用完
	public static final int ArenaRecoverIsNotOk = 4403;	//竞技场战斗冷却中
	public static final int NotEnoughBattleArenaTimes = 4404;// 竞技场无可用次数
	public static final int NotEnoughBuyArenaCount = 4405;// 竞技场无可购买次数
	public static final int TargetGpIdIsBattling = 4406;// 挑战的对象正在进行竞技场战斗
	public static final int ArenaRecoverIsOk = 4407;	//竞技场战斗已经冷却完成
	
	//邮件系统
	public static final int MsgNotExist = 4501;//邮件不存在
	public static final int MsgIsNotReward = 4502;//不是带礼包附件的邮件
	public static final int RewardHasReceived = 4503;//邮件附件已经领取
	public static final int SendMsgToStrangerCountMax = 4504;//非好友邮件发送已经达到上限
	public static final int RecvMsgCountMax = 4505;//消息列表已满
	
	// 商店
	public static final int ShopInfoIsNotNew = 4601;// 商店物品已更新，会在购买商店物品时出现
	public static final int AlreadyBuyed = 4602;// 已被购买
	
	//功能建筑
	public static final int MaxAwakeLevel = 4701;//已达最大的觉醒等级
	public static final int IsNotGoldMineBuilding = 4702;//该建筑不是金矿
	public static final int GoldMinePosNotOpen = 4703;//金矿开启的位置数不足
	public static final int IsNotMacrotiaRelicBuilding = 4704;//该建筑不是巨耳遗迹
	public static final int MacrotiaRelicPosNotOpen = 4705;//巨耳遗迹开启的位置不足
	public static final int GuardBuildingIdIsDiff = 4706;//派遣建筑id与建筑id不相符
	
	//成就
	public static final int AchieveAlreadyGot = 6000;//成就已经领取
	public static final int NotReachTheAchieveTarget = 6001;//未达到成就目标
	public static final int AchieveTypeIsNotExists = 6002;//成就任务类型不存在
	//主线任务
	public static final int NotReachTheMainLineTarget = 7000;//未达到主线任务目标
	
	//留言板
	public static final int NotMessageBoardPermission = 8000;//没有留言权限
	
	//探险
	public static final int NotEnoughExploreTimes = 8100;//没有可探险次数
	public static final int PlayerLockedByEvilWitch = 8101;//被邪恶巫婆囚禁中
	public static final int ActionIdError = 8102;//该场景中没有对应操作
	public static final int ExploreTypeNotExist = 8103;//探险类型不存在
	public static final int ExploreSceneIdNotExist = 8104;//探险类型不存在
	public static final int ExploreTypeError = 8105;//该场景探险类型与当前探险类型不一致
	public static final int ExploreAvailableCountNotZero = 8106;//当前可探险次数不是0
	
	//仓库
	public static final int ItemCanNotSell = 8201;//该物品不可出售
	public static final int StorehouseIsFull = 8202;//仓库已满
	
	//暗影岛
	public static final int ShadowIslandIsNotRewardStage = 8301;//暗影岛当前不是奖励关卡
	public static final int ShadowIslandIsNotBattleStage = 8302;//暗影岛当前不是战斗关卡
	public static final int ShadowIslandIsFinish = 8303;//暗影岛已通关
	public static final int NotShadowIslandIsAvailableResetCount = 8304;//已经没有暗影岛重置次数
	public static final int ExistDeadTeamMember = 8305;//队伍中包含已经死亡的成员

	//黑水财团
	public static final int BusinessmanIsMaxLevel = 8401;//商人已经是最大等级
	public static final int NotEnoughConvoyTimes = 8402;//护送次数已用完
	public static final int NotEnoughPillageTimes = 8403;//掠夺次数已用完
	public static final int HasConvoying = 8404;//有未完成的护送
	public static final int NotBePillaged = 8405;//不可被掠夺
	public static final int NotEnoughLevelOfPillage = 8406;//掠夺等级不足
	public static final int ConvoyIsNotExists = 8407;//护送不存在
	public static final int ConvoyIsNotEnd = 8408;//护送未完成
	public static final int ConvoyIsEnd = 8409;//护送已完成
	public static final int ConvoyIsBePillaging = 8410;//护送正被掠夺中
	
	//冒牌魔法师
	public static final int FakeMagicialIsNotOpen = 8501;//冒牌魔法师未开启
	public static final int MaxFakeMagicialMp = 8502;//已达最大的冒牌魔法师魔法值
	public static final int NotMaxFakeMagicialMp = 8503;//魔法师的魔法值未充满
	
	//体力购买
	public static final int BuyTimesNotEnouch = 8601;//体力购买次数不足
}
