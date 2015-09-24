/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yonder.game.server.command;

import com.yonder.game.param.Param;
import com.yonder.game.param.ResultMap;

public interface ICommand {
	
    public ResultMap handle(Param param);
    
    public static final short MAINTAINING = -1;

    public static final short TEST_HELPER = 40;//测试辅助接口
    public static final short GetBattleAgentInfo = 41;//测试获取战斗任务信息
    public static final short GetInitBattleAgentInfo = 42;//获取指定id角色初始（1星，1级，没有装备，没有符文）战斗信息
    public static final short GetSkillEventInfo = 43;//获取技能信息
    
    //全局接口
    public static final short SynGameConfigs = 20000;//游戏配置同步接口
    public static final short LoadGameConfigs = 20017;//游戏配置同步接口(加载最新配置版本信息)
    public static final short SynClock = 20001;//同步时间
    public static final short GetGameProfile = 20002;//获取游戏档案
    public static final short CreatePlayerInfo = 20003;//创建玩家档案
    public static final short GetPlayerUnlockIcons = 20004;//获取玩家已解锁icon信息
    public static final short UnlockPlayerIcon = 20005;//解锁玩家icon
    public static final short ChangePlayerIcon = 20006;//修改玩家icon
    public static final short ChangePlayerName = 20007;//修改玩家名称
    public static final short GetTargetGameProfile = 20008;//获取拜访对象玩家档案
    public static final short AddFeedback = 20009;//添加用户反馈信息
    public static final short SkipGuideTask = 20010;//跳过新手引导
    public static final short GetTargetInfo = 20011;//获取指定玩家基础信息（队伍、名称等级等信息）
    public static final short PraiseTargetPlayer = 20012;//为指定玩家点赞
    
    //家园
    public static final short GetLandInfo = 21001;//获取全部家园信息
    public static final short GetHomeBuildings = 21002;//获取指定地块建筑信息
    public static final short AddHomeBuilding = 21003;//添加家园建筑
    public static final short MoveHomeBuilding = 21004;//移动家园建筑
    public static final short RemoveHomeBuilding = 21005;//移除家园建筑
    public static final short OpenLand = 21006;//开启新地块
    
    //精灵
    public static final short AddEidolon = 21101;//添加精灵
    public static final short GetEidolonInfo = 21102;//获取精灵信息
    public static final short EidolonAdvanceStar = 21105;//精灵升星
    public static final short EidolonAdvanceRuneStep = 21106;//精灵符文进阶
    public static final short GetSummonEidolonInfo = 21109;//加载召唤信息
    public static final short SummonEidolon = 21110;//召唤精灵
    public static final short FeedEidolonExp = 21111;//用经验材料喂养精灵
    
    //副本
    public static final short BeginStoryBattle = 21201;//开始副本战斗
    public static final short StoryBattleCheck = 21202;//副本战斗过程验证
    public static final short StoryQuickBattle = 21204;//副本扫荡
    public static final short BuyStoryBattleBattleCount = 21205;//购买副本战斗次数
    
    //英雄副本
    public static final short BeginHeroStoryBattle = 21211;//开始英雄副本战斗
    public static final short HeroStoryBattleCheck = 21212;//英雄副本战斗过程验证
    public static final short LoadHeroStoryInfo = 21213;//加载英雄副本信息
    public static final short LoadHeroStoryMenuInfo = 21214;//加载英雄副本菜单信息列表
    
    //竞技场
    public static final short LoadArenaEnemies = 21300;//获取竞技场敌人列表
    public static final short ArenaBattle = 21301;//竞技场战斗接口
    public static final short LoadArenaLogDetail = 21302;//获取被打的战斗日志信息
    public static final short LoadTopArenaEnemies = 21303;//获取前n名的玩家
    public static final short BuyArenaBattleTimes = 21305;//购买竞技场次数
    public static final short ArenaBattleCdSpeedUp = 21306;//加速竞技场战斗冷却cd
    
    //砖石商品
    public static final short LoadBoughtShopGoodInfo = 21802;//加载玩家购买过的砖石商品信息
    public static final short LoadPpBuyInfo = 21803;//加载体力购买信息
    public static final short BuyPp = 21804;//购买体力或金币
    public static final short LoadCoinBuyInfo = 21805;//加载金币购买信息
    public static final short BuyCoin = 21806;//购买金币
    
    //好友系统
    public static final short LoadFriend = 22001;//加载好友信息
    public static final short RequestAddFriend = 22003;//请求添加 好友
    public static final short AgreeFriendRequest = 22004;//同意好友请求
    public static final short RejectFriendRequest = 22005;//拒绝好友请求
    public static final short DeleteFriend = 22006;//删除好友
    public static final short FindFriendByName = 22007;//根据玩家名称查找好友
    public static final short SuggestFriend = 22008;//推荐好友
    
    //消息系统
    public static final short LoadMsg = 22101;//加载玩家的消息
    public static final short SendFriendMsg = 22102;//发送好友消息
    public static final short ReadMsg = 22103;//阅读消息
    public static final short DelMsg = 22104;//删除消息
    public static final short RecvMsg = 22105;//收取新邮件，给客户端在邮件界面轮询调用
    public static final short ReceiveMsgReward = 22106;//领取邮件附件
    
    //商店
    public static final short GetShopInfo = 22500;//获取商店商品信息
    public static final short BuyShopInfo = 22501;//购买商店商品
    public static final short RefreshShopInfo = 22502;//手动付费刷新商店
    
    //每日任务
    public static final short LoadDailyTask = 22600;
    public static final short RecvDailyTask = 22603;
    
    //活动管理
    public static final short LoadOperateEvents = 23002;//加载当前活动信息
    public static final short FetchOperateEventReward = 23003;//领取活动奖励
    public static final short LoadOperateRankInfo = 23004;//加载活动排行榜信息
    
    //聊天系统
    public static final short RecvChatMessage = 23200;//接收聊天消息
    public static final short SendChatCommand = 23201;//发送聊天消息

    //公会系统
    public static final short SocietyCreate = 23301;//创建公会
    public static final short SocietyQuery = 23302;//公会搜索
    public static final short SocietyList = 23303;//获取推荐公会列表
    public static final short SocietyDetail = 23304; //加载指定工会详细信息
    public static final short SocietyApply = 23305;//申请加入工会
    public static final short SocietyApproval = 23306;//批准加入工会
    public static final short SocietyRefuse = 23307;//拒绝加入公会
    public static final short SocietyUpdateNotice = 23308;//更新公会公告
    public static final short SocietyExit = 23309;//退出公会
    public static final short SocietyKickOut = 23310;//踢出公会
    public static final short SocietySetViceLord = 23311;//任命副会长
    public static final short SocietyCancelViceLord = 23312;//取消副会长
    public static final short SocietyLog = 23313;//公会日志
    public static final short SocietyApplyList = 23314;//公会申请成员列表
    public static final short SocietyUpdateLogo = 23315;//更新公会logo
    public static final short SocietyUpdateDes = 23316;//更新公会描述
    public static final short AssignSocietyLord = 23317;//转让会长
    public static final short SocietyNotice = 23318;//公会公告获取
    public static final short SocietyCancleApply = 23319;//取消加入公会申请
    public static final short PlayerSocietyApplyList = 23320;//获取个人申请的公会列表
    public static final short SocietyMsg = 23321;//加载自己的公会消息列表
    public static final short SocietyDevote = 23322;//公会捐献
    public static final short SocietyDevoteInfo = 23323;//加载公会捐献信息（当前可以捐献次数）
    public static final short SocietyWorshipEncourageInfo = 23324;//加载公会膜拜、激励信息（当前可以膜拜、激励次数）
    public static final short SocietyWorship = 23325;//公会膜拜
    public static final short SocietyEncourage = 23326;//公会激励
    public static final short SocietyRainbowFruitInfo = 23327;//获取公会彩虹果信息
    public static final short SocietyRainbowFruitPick = 23328;//采摘公会彩虹果
    public static final short SocietySealInfo = 23329;//获取魔物封印信息
    public static final short SocietySealAdd = 23330;//参与魔物封印
    public static final short SocietySealCallTeammates = 23331;//召唤成员
    public static final short SocietyDarkInvasionInfo = 23332;//获取黑暗侵袭信息
    public static final short SocietyDarkInvasionBeginBattle = 23333;//开始黑暗侵袭战斗
    public static final short SocietyDarkInvasionCheckBattle = 23334;//验证黑暗侵袭战斗
    public static final short SocietyBossInfo = 23335;//公会boss信息获取
    public static final short SocietyBossActiveOpen = 23336;//公会boss战斗活动开启
    public static final short SocietyBossBattle = 23337;//公会boss战斗
    public static final short SocietyBattleInfo = 23338;//获取公会战信息
    public static final short SocietyBattleSign = 23339;//公会战报名
    public static final short SocietyBattleSetDefends = 23340;//设置公会战防守队伍
    public static final short SocietyBattleAutoSetAllDefends = 23341;//自动设置公会战城池防守队伍
    public static final short SocietyBattleBeginBattle = 23342;//开始公会战战斗
    public static final short SocietyBattleCheckBattle = 23343;//公会战战斗验证
    public static final short SocietyBattleBadageRanks = 23344;//获取公会战徽章排行榜信息
    public static final short SocietyBattleCityInfo = 23345;//加载城池剩余徽章数
    public static final short SocietyBattleCityLogs = 23346;//加载城池战斗日志
    
    //主角
    public static final short GetHeroInfo = 25000;//获取主角信息
    public static final short HeroAddEquipment = 25001;//添加主角装备
    public static final short HeroRemoveEquipment = 25002;//卸下主角装备
    public static final short BeginHeroAdvanceStarBattle = 25003;//开始主角升星战斗
    public static final short HeroAdvanceStarBattleCheck = 25004;//验证主角升星战斗
    public static final short HeroEquipmentAdvanceStep = 25007;//强化主角装备
    public static final short HeroEquipmentFusion = 25008;//融合主角装备
    public static final short HeroEquipmentAutoAdvanceStep = 25009;//主要装备一键强化
    
    
    //队伍、布阵
    public static final short GetTeamInfo = 25100;//获取玩家队伍信息
    public static final short UpdateDefendTeam = 25101;//更新防守队伍成员信息
    public static final short UpdateAttackTeam = 25103;//更新进攻队伍成员信息
    
    //签到
    public static final short GetSignInInfo = 25200;//获取签到信息
    public static final short SignIn = 25201;//签到
    
    //时光圣地
    public static final short GetShrineInfo = 25300;//获取时光圣地信息
    public static final short SendEidolonToShrine = 25301;//将精灵放入圣地
    public static final short CollectEidolonExp = 25302;//收取时光圣地精灵经验
    
    //功能建筑
    public static final short ChangeSpringNature = 25400;//变换元素之泉的属系
    public static final short GetSpringInfo = 25408;//获取元素之泉的信息
    public static final short CharactarAwakening = 25401;//觉醒祭坛角色觉醒   
    public static final short GetGoldMineInfo = 25402;//获取金矿的信息
    public static final short SendEidolonForMining = 25403;//金矿派遣或撤销精灵
    public static final short CollectCoinFromGoldMine = 25404;//从金矿收取金币
    public static final short GetMacrotiaRelicInfo = 25405;//获取巨耳遗迹的信息
    public static final short SendEidolonToMacrotiaRelic = 25406;//派遣或取消精灵
    public static final short GainMacrotiaRelicReward = 25407;//收获
    
    //成就系统
    public static final short QueryAchieveSchedule = 27000;//查看成就进度
    public static final short RecieveAchieveReward = 27001;//领取成就奖励
    
    //主线任务
    public static final short RecieveMainLineReward = 28000;//领取任务奖励
    public static final short LoadMainLineInfo = 28001;//加载主线任务信息
    
    //留言板
    public static final short AddMessageBoard = 28100;//添加留言板消息
    public static final short ClearMessageBoard = 28101;//清空留言板
    public static final short DelMessageBoard = 28102;//删除某条留言消息
    public static final short LoadMessageBoard = 28103;//加载留言板信息
    public static final short SetMessageBoardPermission = 28104;//设置留言板权限
    
    //探险
    public static final short LoadExplore = 28200;//加载探险事件id
    public static final short ExploreAction = 28201;//探险操作
    public static final short ExploreBattleCheck = 28202;//探险战斗验证
    public static final short LoadExploreStatus = 28203;//加载玩家当前探险状态
    public static final short ResetExploreCount = 28204;//重置玩家探险次数
    
    //仓库
    public static final short SellItem = 28300;//卖出普通仓库物品
    public static final short SellTempItem = 28301;//卖出临时背包内的物品
    public static final short CollectTempItem = 28302;//把临时背包内物品放到普通仓库中
    public static final short CollectAllTempItem = 28303;//把临时背包内物品全部放到普通仓库中
    public static final short SellAllTempItem = 28304;//把临时背包内物品全部卖出
    
    //暗影岛
    public static final short LoadShadowIslandInfo = 28400;//加载暗影岛信息
    public static final short UpdateShadowIslandPlayerTeam = 28401;//更新暗影岛布阵
    public static final short BeginShadowIslandBattle = 28402;//开始暗影岛战斗
    public static final short CheckShadowIslandBattle = 28403;//检测暗影岛战斗
    public static final short OpenShadowIslandTreasure = 28404;//打开暗影岛宝箱
    public static final short ResetShadowIslandInfo = 28405;//重置暗影岛
    public static final short LoadShadowIslandId = 28406;//加载暗影岛当前关卡id
    
    //黑水财团
    public static final short LoadConsortiumInfo = 28500;//加载黑水财团信息
    public static final short UpgradeBusinessman = 28501;//升级护送商人
    public static final short ConvoyMaterials = 28502;//护送物资
    public static final short FindPillageTargets = 28503;//搜索可掠夺对象
    public static final short BeginPillageBattle = 28504;//掠夺战斗开始
    public static final short CheckPillageBattle = 28505;//掠夺战斗验证
    public static final short GetConvoyReward = 28506;//领取护送奖励
    public static final short FindConsortiumLog = 28507;//查看日志记录
    public static final short PlaybackBattle = 28508;//回放战斗
    public static final short CompleteConvoyImmediate = 28509;//立刻完成护送
    
    //战斗预留
    public static final short LoadBattlePreviewParam = 28600;//获取战斗预览参数
    
    //冒牌魔法师
    public static final short GetFakeMagicialInfo = 28700;//获取魔法师信息
    public static final short QuickAddMagicialMp = 28701;//魔法水晶快速增加魔法师魔力值
    public static final short Wish = 28702;//许愿
}
